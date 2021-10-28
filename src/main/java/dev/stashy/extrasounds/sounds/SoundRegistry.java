package dev.stashy.extrasounds.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SoundRegistry
{
    static SoundEvent register(Identifier id)
    {
        var e = new SoundEvent(id);
        Registry.register(Registry.SOUND_EVENT, id, e);
        return e;
    }

    static SoundEvent register(String id)
    {
        return register(new Identifier("extrasounds:" + id));
    }
}
