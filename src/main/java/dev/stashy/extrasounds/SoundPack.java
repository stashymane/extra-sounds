package dev.stashy.extrasounds;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.nio.file.Path;

public class SoundPack
{
    public static RuntimeResourcePack rp = RuntimeResourcePack.create("extrasounds:sounds");

    public static void init()
    {
        Registry.ITEM.getIds().forEach(SoundPack::loadItem);
        RegistryEntryAddedCallback.event(Registry.ITEM).register((rawId, id, object) -> loadItem(id));
        RRPCallback.AFTER_VANILLA.register(a -> a.add(rp));
        rp.dump(Path.of("D:\\dump"));
    }

    public static void loadItem(Identifier id)
    {
        Identifier snd = new Identifier("extrasounds:item.click." + id.getPath());
        rp.addAsset(Identifier.tryParse("extrasounds:sounds.json"),
                    ("{\"" + snd.getPath() + "\": {\"sounds\": [ \"minecraft:random/wood_click\" ]}}").getBytes());

        Registry.register(Registry.SOUND_EVENT, snd, new SoundEvent(snd));
    }
}
