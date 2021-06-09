package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockItem.class)
public abstract class BlockItemSound implements ItemSoundContainer
{
    private InventorySound invSound;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(Block block, Item.Settings settings, CallbackInfo ci)
    {
        float baseVol = 1f;
        SoundEvent place = block.getDefaultState().getSoundGroup().getPlaceSound();
        if (place.equals(SoundEvents.BLOCK_GRASS_PLACE))
            baseVol = 0.7f;
        else if (place.equals(SoundEvents.BLOCK_ANVIL_PLACE))
            baseVol = 0.2f;
        else if (place.equals(SoundEvents.BLOCK_SLIME_BLOCK_PLACE))
            baseVol = 0.7f;
        else if (place.equals(SoundEvents.BLOCK_WOOL_PLACE))
            baseVol = 1.2f;

        if (block.equals(Blocks.TRIPWIRE) || block.equals(Blocks.REDSTONE_WIRE) || block.equals(Blocks.COBWEB))
            place = SoundEvents.BLOCK_SAND_PLACE;
        else if (block instanceof BannerBlock)
            place = SoundEvents.BLOCK_WOOL_PLACE;

        invSound = new InventorySound(place, baseVol);
    }

    @Override
    public InventorySound getInventorySound()
    {
        return invSound;
    }
}
