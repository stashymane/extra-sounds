package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.BoneMealItem;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BoneMealItem.class)
public class BonemealItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.BLOCK_GRAVEL_PLACE, 1f);

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
