package dev.stashy.extrasounds.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;

public class SoundRegistry
{
    static SoundEvent register(Identifier id)
    {
        var e = SoundEvent.of(id);
        try
        {
            Registry.register(Registries.SOUND_EVENT, id, e);
        }
        catch (IllegalStateException exception)
        {
            LogManager.getLogger()
                      .error("Failed to register SoundEvent - please report this on ExtraSounds' Github page!");
            exception.printStackTrace();
        }
        return e;
    }

    static SoundEvent register(String id)
    {
        return register(new Identifier("extrasounds:" + id));
    }
}
