package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({MilkBucketItem.class, ExperienceBottleItem.class})
public class DefaultBucketFillSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.ITEM_BUCKET_FILL, 1f);

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
