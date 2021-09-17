package dev.stashy.extrasounds;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundPackLoader
{
    public static RuntimeResourcePack rp = RuntimeResourcePack.create("extrasounds:sounds");
    private static final Identifier soundsJsonId = new Identifier("extrasounds:sounds.json");

    public static void init()
    {
        Registry.ITEM.getIds().forEach(SoundPackLoader::loadItem);
        Registry.BLOCK.getIds().forEach(SoundPackLoader::loadBlock);
        RegistryEntryAddedCallback.event(Registry.ITEM).register((rawId, id, object) -> loadItem(id));
        RegistryEntryAddedCallback.event(Registry.BLOCK).register(((rawId, id, object) -> loadBlock(id)));
        RRPCallback.AFTER_VANILLA.register(a -> a.add(rp));
    }

    public static void loadItem(Identifier id)
    {
        Identifier snd = new Identifier("extrasounds:item.click." + id.getPath());
        //TODO add sound asset
        Registry.register(Registry.SOUND_EVENT, snd, new SoundEvent(snd));
    }

    public static void loadBlock(Identifier id)
    {
        //Block b = Registry.BLOCK.get(id);
        //TODO replace current sound to block group sound
    }
}
