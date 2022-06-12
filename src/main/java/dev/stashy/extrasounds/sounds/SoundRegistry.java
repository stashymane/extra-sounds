package dev.stashy.extrasounds.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;

public class SoundRegistry
{
    static SoundEvent register(Identifier id)
    {
        var e = new SoundEvent(id);
        try
        {
            Registry.register(Registry.SOUND_EVENT, id, e);
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
