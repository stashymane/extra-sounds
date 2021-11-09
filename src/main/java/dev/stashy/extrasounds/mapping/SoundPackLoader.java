package dev.stashy.extrasounds.mapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.stashy.extrasounds.ExtraSounds;
import dev.stashy.extrasounds.debug.DebugUtils;
import dev.stashy.extrasounds.json.SoundEntrySerializer;
import dev.stashy.extrasounds.json.SoundSerializer;
import dev.stashy.extrasounds.sounds.Sounds;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SoundPackLoader
{
    private static final RuntimeResourcePack genericPack = RuntimeResourcePack.create("extrasounds");
    private static final Identifier soundsJsonId = new Identifier("extrasounds:sounds.json");
    private static final Logger LOGGER = LogManager.getLogger();

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

        genericPack.addLazyResource(ResourceType.CLIENT_RESOURCES, soundsJsonId, (rrp, identifier) -> {
            var itemMap = Registry.ITEM.getIds().stream().map(id -> {
                var sndId = new Identifier(ExtraSounds.MODID, ExtraSounds.getClickId(id, false));
                if (!Registry.SOUND_EVENT.containsId(sndId))
                    Registry.register(Registry.SOUND_EVENT, sndId, new SoundEvent(sndId));

                var snd = Sounds.aliased(Sounds.ITEM_PICK);
                if (mappers.containsKey(id.getNamespace()))
                    snd = mappers.get(id.getNamespace()).itemSoundGenerator().apply(id);
                else if (Registry.ITEM.get(id) instanceof BlockItem b)
                    try
                    {
                        snd = Sounds.event(
                                b.getBlock().getSoundGroup(b.getBlock().getDefaultState()).getPlaceSound().getId());
                    }
                    catch (Exception e)
                    {
                    }
                return new Pair<>(sndId.getPath(), snd);
            }).collect(Collectors.toMap(Pair::getLeft, Pair::getRight, (a, b) -> b, HashMap::new));

            var json = gson.toJson(itemMap).getBytes();
            DebugUtils.exportSoundsJson(json);
            DebugUtils.exportGenerators();
            return json;
        });

        RRPCallback.BEFORE_VANILLA.register((packs) -> packs.add(genericPack));
        registerPack("keyboard-sounds");
    }

    private static void registerPack(String name)
    {
        FabricLoader.getInstance().getModContainer(ExtraSounds.MODID)
                    .map(container -> ResourceManagerHelper.registerBuiltinResourcePack(
                            new Identifier(ExtraSounds.MODID, name),
                            container, ResourcePackActivationType.NORMAL))
                    .filter(success -> !success)
                    .ifPresent(success -> LOGGER.warn("Could not register built-in resource pack."));
    }
}
