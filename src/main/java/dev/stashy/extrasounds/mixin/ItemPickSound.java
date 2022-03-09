package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.SoundManager;
import dev.stashy.extrasounds.sounds.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class ItemPickSound
{
    @Shadow
    @Final
    public PlayerEntity player;

    @Inject(method = "addPickBlock", at = {
            @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;set(ILjava/lang/Object;)Ljava/lang/Object;"),
            @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;swapSlotWithHotbar(I)V"),
            @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I")
    })
    void pickSound(ItemStack stack, CallbackInfo ci)
    {
        if (!player.getMainHandStack().getItem().equals(stack.getItem()))
            SoundManager.playSound(stack, SoundType.PICKUP);
    }
}
