package dev.stashy.extrasounds;

import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public interface ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.BLOCK_GLASS_STEP, 1f);

    default InventorySound getInventorySound()
    {
        return snd;
    }

    void initSound(Identifier id);
}
