package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({FishingRodItem.class, ItemFrameItem.class, BoatItem.class, CrossbowItem.class, BowItem.class, MushroomStewItem.class})
public class DefaultWoodItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.BLOCK_WOOD_PLACE, 1f);
    ;

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }

}
