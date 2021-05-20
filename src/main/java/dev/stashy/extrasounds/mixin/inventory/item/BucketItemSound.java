package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BucketItem.class)
public class BucketItemSound implements ItemSoundContainer
{
    @Shadow
    @Final
    private Fluid fluid;

    InventorySound snd;

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }

    @Override
    public void initSound(Identifier id)
    {
        SoundEvent e;
        if (fluid == Fluids.LAVA)
            e = SoundEvents.ITEM_BUCKET_FILL_LAVA;
        else if (fluid == Fluids.EMPTY)
            e = SoundEvents.BLOCK_NETHER_GOLD_ORE_PLACE;
        else
            e = SoundEvents.ITEM_BUCKET_FILL;
        snd = new InventorySound(e, 1f);
    }
}
