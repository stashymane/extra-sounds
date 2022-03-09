package dev.stashy.extrasounds.sounds;

import dev.stashy.extrasounds.Mixers;
import net.minecraft.sound.SoundCategory;

public enum SoundType
{
    PICKUP(1f, Mixers.INVENTORY),
    PLACE(0.9f, Mixers.INVENTORY),
    HOTBAR(1f, Mixers.SCROLL),
    EFFECT(1f, Mixers.EFFECTS),
    CHAT(1f, Mixers.CHAT),
    TYPING(1f, Mixers.TYPING);

    public final float pitch;
    public final SoundCategory category;

    SoundType(float pitch, SoundCategory category)
    {
        this.pitch = pitch;
        this.category = category;
    }
}
