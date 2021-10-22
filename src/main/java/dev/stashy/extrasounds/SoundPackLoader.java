package dev.stashy.extrasounds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.stashy.extrasounds.json.SoundSerializer;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.block.Block;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundPackLoader
{
    private static RuntimeResourcePack genericPack = RuntimeResourcePack.create("extrasounds");
    private static final Identifier soundsJsonId = new Identifier("extrasounds:sounds.json");
    private static final Map<String, SoundEntry> entries = new HashMap<>();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Sound.class, new SoundSerializer())
            .create();

    public static void init()
    {
        Registry.ITEM.getIds().forEach(SoundPackLoader::loadItem);
        Registry.BLOCK.getIds().forEach(SoundPackLoader::loadBlock);
        RegistryEntryAddedCallback.event(Registry.ITEM).register((rawId, id, object) -> loadItem(id));
        RegistryEntryAddedCallback.event(Registry.BLOCK).register(((rawId, id, object) -> loadBlock(id)));
        genericPack.addLazyResource(ResourceType.CLIENT_RESOURCES, soundsJsonId, (runtimeResourcePack, identifier) -> {
            System.out.println(gson.toJson(entries));
            return gson.toJson(entries).getBytes();
        });
        RRPCallback.BEFORE_VANILLA.register((packs) -> packs.add(genericPack));
    }

    public static void loadItem(Identifier id)
    {
        Identifier snd = new Identifier("extrasounds:item.click." + id.getPath());
        entries.put(snd.getPath(), new SoundEntry(List.of(
                new Sound("extrasounds:generic.click", 1f, 1f, 1,
                          Sound.RegistrationType.SOUND_EVENT, false, false, 0)
        ), false, null));
        Registry.register(Registry.SOUND_EVENT, snd, new SoundEvent(snd));
    }

    public static void loadBlock(Identifier id)
    {
        Block b = Registry.BLOCK.get(id);
        String soundId = b.getSoundGroup(b.getDefaultState()).getPlaceSound().getId().toString();
        entries.put("item.click." + id.getPath(),
                    new SoundEntry(List.of(
                            new Sound(soundId, 0.2f, 1.7f, 1, Sound.RegistrationType.SOUND_EVENT, false, false, 0)
                    ), false, null));
    }
}
