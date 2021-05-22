package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.CompassItem;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({CompassItem.class})
public class DefaultEquipItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1f);

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
