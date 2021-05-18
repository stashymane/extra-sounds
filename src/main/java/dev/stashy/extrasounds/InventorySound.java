package dev.stashy.extrasounds;

import net.minecraft.sound.SoundEvent;

public class InventorySound
{
    public final SoundEvent sound;
    public final float baseVol;

    public InventorySound(SoundEvent sound, float baseVol)
    {
        this.sound = sound;
        this.baseVol = baseVol;
    }

}
