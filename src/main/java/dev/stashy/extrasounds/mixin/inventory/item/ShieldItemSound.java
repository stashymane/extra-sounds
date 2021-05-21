package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.ShieldItem;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShieldItem.class)
public class ShieldItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1);;

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
