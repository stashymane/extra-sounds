package dev.stashy.extrasounds;

import net.minecraft.sound.SoundEvents;

public interface ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.BLOCK_GLASS_STEP, 1f);

    default InventorySound getInventorySound()
    {
        return snd;
    }
}
