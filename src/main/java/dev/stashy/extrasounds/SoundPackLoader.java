package dev.stashy.extrasounds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.stashy.extrasounds.debug.DebugUtils;
import dev.stashy.extrasounds.json.SoundSerializer;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
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

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Sound.class, new SoundSerializer())
            .create();

    public static void init()
    {
        genericPack.addLazyResource(ResourceType.CLIENT_RESOURCES, soundsJsonId,
                                    (runtimeResourcePack, identifier) -> generateJson());
        RRPCallback.BEFORE_VANILLA.register((packs) -> packs.add(genericPack));
    }

    private static byte[] generateJson()
    {
        Map<String, SoundEntry> entries = new HashMap<>();
        Registry.ITEM.getIds().forEach((i) -> loadItem(i, entries));
        Registry.BLOCK.getIds().forEach((i) -> loadBlock(i, entries));
        byte[] json = gson.toJson(entries).getBytes();
        DebugUtils.exportSoundsJson(json);
        return json;
    }

    public static void loadItem(Identifier id, Map<String, SoundEntry> entries)
    {
        Identifier snd = new Identifier("extrasounds:item.click." + id.getPath());
        entries.put(snd.getPath(), new SoundEntry(List.of(
                new Sound("extrasounds:generic.click", 1f, 1f, 1,
                          Sound.RegistrationType.SOUND_EVENT, false, false, 0)
        ), false, null));
        Registry.register(Registry.SOUND_EVENT, snd, new SoundEvent(snd));
    }

    public static void loadBlock(Identifier id, Map<String, SoundEntry> entries)
    {
        Block b = Registry.BLOCK.get(id);
        String soundId = b.getSoundGroup(b.getDefaultState()).getPlaceSound().getId().toString();
        float vol = 0.2f;
        if (soundId.equals("minecraft:block.grass.place") || soundId.equals("mineraft:block.slime_block.place"))
            vol = 0.1f;
        entries.put("item.click." + id.getPath(),
                    new SoundEntry(List.of(
                            new Sound(soundId, vol, 1.7f, 1, Sound.RegistrationType.SOUND_EVENT, false, false, 0)
                    ), false, null));
    }
}
