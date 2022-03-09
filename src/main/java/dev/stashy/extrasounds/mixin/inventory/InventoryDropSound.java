package dev.stashy.extrasounds.mixin.inventory;

import dev.stashy.extrasounds.Mixers;
import dev.stashy.extrasounds.SoundManager;
import dev.stashy.extrasounds.sounds.Sounds;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class InventoryDropSound
{
    @Inject(at = @At("TAIL"), method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;")
    private void dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir)
    {
        if (retainOwnership && !stack.isEmpty())
        {
            float range = 0.1f;
            float n = 1f + range *
                    (1f * stack.getItem().getMaxCount() / stack.getCount()) - range / 2;
            SoundManager.playSound(Sounds.ITEM_DROP, n, Mixers.INVENTORY);
        }
    }
}
