package dev.stashy.extrasounds.mapping;

import net.minecraft.client.sound.SoundEntry;

import javax.annotation.Nonnull;

public class SoundDefinition
{
    public SoundEntry pickup;
    public SoundEntry place = null;
    public SoundEntry hotbar = null;

    public SoundDefinition(SoundEntry sound)
    {
        this(sound, null, null);
    }

    public SoundDefinition(@Nonnull SoundEntry pickup, SoundEntry place, SoundEntry hotbar)
    {
        this.pickup = pickup;
        this.place = place;
        this.hotbar = hotbar;
    }

    public static SoundDefinition of(@Nonnull SoundEntry pickup, SoundEntry place, SoundEntry hotbar)
    {
        return new SoundDefinition(pickup, place, hotbar);
    }

    public static SoundDefinition of(@Nonnull SoundEntry sound)
    {
        return new SoundDefinition(sound);
    }
}
