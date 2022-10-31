package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.handlers.InventoryEventHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class HotbarMixin
{
    @Shadow
    public int selectedSlot;

    @Shadow
    public abstract ItemStack getMainHandStack();

    private int lastSelectedSlot = -1;

    @Inject(at = @At(value = "TAIL"), method = "scrollInHotbar(D)V")
    private void hotbarScroll(double scrollAmount, CallbackInfo ci)
    {
        if (scrollAmount != 0)
            playMainHandSound();
    }

    @Inject(at = @At(value = "FIELD", ordinal = 0, shift = At.Shift.AFTER, target = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I"), method = "addPickBlock(Lnet/minecraft/item/ItemStack;)V")
    private void pickBlockSelect(ItemStack stack, CallbackInfo ci)
    {
        if (lastSelectedSlot != selectedSlot)
        {
            playMainHandSound();
            lastSelectedSlot = selectedSlot;
        }
    }

    @Inject(at = @At(value = "INVOKE", ordinal = 1, shift = At.Shift.AFTER, target = "Lnet/minecraft/util/collection/DefaultedList;set(ILjava/lang/Object;)Ljava/lang/Object;"), method = "addPickBlock(Lnet/minecraft/item/ItemStack;)V")
    private void pickBlockAdd(ItemStack stack, CallbackInfo ci)
    {
        playMainHandSound();
    }

    @Inject(at = @At(value = "INVOKE", ordinal = 1, shift = At.Shift.AFTER, target = "Lnet/minecraft/util/collection/DefaultedList;set(ILjava/lang/Object;)Ljava/lang/Object;"), method = "swapSlotWithHotbar(I)V")
    private void pickBlockMove(int slot, CallbackInfo ci)
    {
        playMainHandSound();
    }

    private void playMainHandSound()
    {
        InventoryEventHandler.INSTANCE.hotbar(getMainHandStack().getItem());
    }
}
