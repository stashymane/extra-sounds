package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.block.Block;
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
    private InventorySound sound;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(Block block, Item.Settings settings, CallbackInfo ci)
    {
        float baseVol = 1f;
        SoundEvent place = block.getDefaultState().getSoundGroup().getPlaceSound();
        sound = new InventorySound(place, baseVol);
    }

    @Override
    public InventorySound getInventorySound()
    {
        return sound;
    }
}
