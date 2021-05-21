package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.OnAStickItem;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OnAStickItem.class)
public class OnAStickItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.BLOCK_WOOD_PLACE, 1f);

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
