package dev.stashy.extrasounds.mapping;

import net.minecraft.client.sound.SoundEntry;
import org.jetbrains.annotations.NotNull;

public class SoundDefinition
{
    public SoundEntry pickup;
    public SoundEntry place = null;
    public SoundEntry hotbar = null;

    public SoundDefinition(SoundEntry sound)
    {
        this(sound, null, null);
    }

    public SoundDefinition(@NotNull SoundEntry pickup, SoundEntry place, SoundEntry hotbar)
    {
        this.pickup = pickup;
        this.place = place;
        this.hotbar = hotbar;
    }

    public static SoundDefinition of(@NotNull SoundEntry pickup, SoundEntry place, SoundEntry hotbar)
    {
        return new SoundDefinition(pickup, place, hotbar);
    }

    public static SoundDefinition of(@NotNull SoundEntry sound)
    {
        return new SoundDefinition(sound);
    }
}
