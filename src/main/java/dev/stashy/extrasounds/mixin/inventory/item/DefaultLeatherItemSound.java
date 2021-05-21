package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.LeadItem;
import net.minecraft.item.SaddleItem;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ElytraItem.class, SaddleItem.class, LeadItem.class})
public class DefaultLeatherItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1f);;

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
