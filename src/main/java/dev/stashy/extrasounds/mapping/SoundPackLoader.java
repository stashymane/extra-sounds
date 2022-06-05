package dev.stashy.extrasounds.mapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import dev.stashy.extrasounds.ExtraSounds;
import dev.stashy.extrasounds.debug.DebugUtils;
import dev.stashy.extrasounds.json.SoundEntrySerializer;
import dev.stashy.extrasounds.json.SoundSerializer;
import dev.stashy.extrasounds.sounds.SoundType;
import dev.stashy.extrasounds.sounds.Sounds;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.item.BlockItem;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class SoundPackLoader
{
    private static final int CACHE_VERSION = 1;
    private static final RuntimeResourcePack genericPack = RuntimeResourcePack.create("extrasounds");
    private static final Identifier soundsJsonId = new Identifier("extrasounds:sounds.json");
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Path cachePath = FabricLoader.getInstance().getConfigDir().resolve("extrasounds.cache.json");

    public static List<RuntimeResourcePack> packs = Collections.emptyList();
    public static Map<String, SoundGenerator> mappers = new HashMap<>();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(SoundEntry.class, new SoundEntrySerializer())
            .registerTypeAdapter(Sound.class, new SoundSerializer())
            .create();

    public static void init()
    {
        FabricLoader.getInstance().getEntrypoints(ExtraSounds.MODID, SoundGenerator.class)
                    .forEach(it -> mappers.put(it.namespace(), it));

        String json = getCache();
        if (json == null)
        {
            json = gson.toJson(processSounds());
            writeCache(json);
        }
        var jsonBytes = json.getBytes();

        DebugUtils.exportSoundsJson(jsonBytes);
        DebugUtils.exportGenerators();

        genericPack.addResource(ResourceType.CLIENT_RESOURCES, soundsJsonId, jsonBytes);
        RRPCallback.BEFORE_VANILLA.register((packs) -> packs.add(genericPack));
    }

    private static Map<String, SoundEntry> processSounds()
    {
        return Registry.ITEM.stream().flatMap(item -> {
            var itemId = Registry.ITEM.getId(item);
            SoundDefinition def = new SoundDefinition(Sounds.aliased(Sounds.ITEM_PICK));

            if (mappers.containsKey(itemId.getNamespace()))
                def = mappers.get(itemId.getNamespace()).itemSoundGenerator().apply(item);
            else if (item instanceof BlockItem b)
                try
                {
                    var blockSound =
                            b.getBlock().getSoundGroup(b.getBlock().getDefaultState()).getPlaceSound();
                    def = SoundDefinition.of(Sounds.aliased(blockSound));
                }
                catch (Exception ignored) {}

            List<Pair<SoundEvent, SoundEntry>> entries = new ArrayList<>();
            var pickupSound = registerOrDefault(itemId, SoundType.PICKUP, def.pickup, Sounds.aliased(Sounds.ITEM_PICK));
            entries.add(pickupSound);
            entries.add(registerOrDefault(itemId, SoundType.PLACE, def.place, Sounds.aliased(pickupSound.getLeft())));
            entries.add(registerOrDefault(itemId, SoundType.HOTBAR, def.hotbar, Sounds.aliased(pickupSound.getLeft())));
            return entries.stream();
        }).collect(Collectors.toMap(key -> key.getLeft().getId().getPath(), Pair::getRight));
    }

    private static Pair<SoundEvent, SoundEntry> registerOrDefault(Identifier itemId, SoundType type, SoundEntry entry, SoundEntry defaultEntry)
    {
        var soundEntry = entry == null ? defaultEntry : entry;
        return new Pair<>(registerIfNotExists(itemId, type), soundEntry);
    }

    private static SoundEvent registerIfNotExists(Identifier itemId, SoundType type)
    {
        var soundId = new Identifier(ExtraSounds.MODID, ExtraSounds.getClickId(itemId, type, false));
        var event = new SoundEvent(soundId);
        if (!Registry.SOUND_EVENT.containsId(soundId))
            Registry.register(Registry.SOUND_EVENT, soundId, event);
        return event;
    }

    private static String getCache()
    {
        if (Files.exists(cachePath) && !DebugUtils.noCache)
            try
            {
                var lines = Files.readAllLines(cachePath);
                if (CacheInfo.fromString(lines.get(0)).equals(CacheInfo.getCurrent()))
                {
                    var cache = lines.get(1);
                    var jsonObj = JsonParser.parseString(cache).getAsJsonObject();
                    jsonObj.keySet().forEach((it) -> {
                        var identifier = new Identifier(ExtraSounds.MODID, it);
                        if (!Registry.SOUND_EVENT.containsId(identifier))
                            Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
                    });
                    return cache;
                }
                else
                {
                    DebugUtils.genericLog("Invalidating ExtraSounds cache.");
                    DebugUtils.genericLog("Previous: " + lines.get(0));
                    DebugUtils.genericLog("Current: " + CacheInfo.getCurrent());
                }
            }
            catch (Exception e)
            {
                System.err.println("Failed to load ExtraSounds cache.");
                e.printStackTrace();
            }
        else DebugUtils.genericLog("Cache not found - generating...");
        return null;
    }

    private static void writeCache(String json)
    {
        try
        {
            Files.write(cachePath, (CacheInfo.getCurrent() + "\n" + json).getBytes(),
                        StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            DebugUtils.genericLog("Cache saved.");
        }
        catch (IOException e)
        {
            System.err.println("Failed to save ExtraSounds cache.");
            e.printStackTrace();
        }
    }

    record CacheInfo(int version, int itemCount, String[] mappers)
    {
        public static CacheInfo getCurrent()
        {
            var versionInfos = SoundPackLoader.mappers
                    .values().stream().map(it -> getModVersion(it.modId())).toArray(String[]::new);
            return new CacheInfo(CACHE_VERSION, Registry.ITEM.size(), versionInfos);
        }

        public static CacheInfo fromString(String s)
        {
            try
            {
                var arr = s.split(";");
                return new CacheInfo(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), arr[2].split(","));
            }
            catch (Exception e)
            {
                return new CacheInfo(0, 0, new String[0]);
            }
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof CacheInfo comp)
                return this.version == comp.version
                        && this.itemCount == comp.itemCount
                        && Arrays.equals(this.mappers, comp.mappers);
            return false;
        }

        public String toString()
        {
            return "%d;%d;%s".formatted(version, itemCount, String.join(",", mappers));
        }

        private static String getModVersion(String modId)
        {
            return FabricLoader.getInstance()
                               .getModContainer(modId)
                               .map(modContainer ->
                                            modId + " " + modContainer.getMetadata().getVersion().getFriendlyString())
                               .orElse("nope");
        }
    }
}
