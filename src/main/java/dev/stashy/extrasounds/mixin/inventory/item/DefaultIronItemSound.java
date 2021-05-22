package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShieldItem;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({FlintAndSteelItem.class, ShearsItem.class, ShieldItem.class})
public class DefaultIronItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1f);

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
