package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HoneyBottleItem.class)
public class HoneyBottleItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.BLOCK_CORAL_BLOCK_PLACE, 1f);

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
