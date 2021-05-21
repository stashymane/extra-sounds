package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TridentItem.class)
public class TridentItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.ITEM_TRIDENT_HIT, 1f);

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
