package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 1f);

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
