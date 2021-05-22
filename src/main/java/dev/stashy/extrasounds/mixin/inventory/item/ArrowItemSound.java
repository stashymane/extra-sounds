package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.ArrowItem;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ArrowItem.class})
public class ArrowItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.BLOCK_GILDED_BLACKSTONE_PLACE, 1f);

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
