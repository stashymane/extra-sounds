package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.handlers.InventoryEventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
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

    @Inject(at = @At(value = "TAIL"), method = "scrollInHotbar(D)V")
    private void hotbarScroll(double scrollAmount, CallbackInfo ci)
    {
        if (scrollAmount != 0)
            playMainHandSound(true);
    }

    @Inject(at = @At(value = "FIELD", ordinal = 0, shift = At.Shift.AFTER, target = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I"), method = "addPickBlock(Lnet/minecraft/item/ItemStack;)V")
    private void pickBlockSelect(ItemStack stack, CallbackInfo ci)
    {
        playMainHandSound(false);
    }

    @Inject(at = @At(value = "INVOKE", ordinal = 1, shift = At.Shift.AFTER, target = "Lnet/minecraft/util/collection/DefaultedList;set(ILjava/lang/Object;)Ljava/lang/Object;"), method = "addPickBlock(Lnet/minecraft/item/ItemStack;)V")
    private void pickBlockAdd(ItemStack stack, CallbackInfo ci)
    {
        playMainHandSound(true);
    }

    @Inject(at = @At(value = "INVOKE", ordinal = 1, shift = At.Shift.AFTER, target = "Lnet/minecraft/util/collection/DefaultedList;set(ILjava/lang/Object;)Ljava/lang/Object;"), method = "swapSlotWithHotbar(I)V")
    private void pickBlockMove(int slot, CallbackInfo ci)
    {
        playMainHandSound(true);
    }

    private void playMainHandSound(boolean ignoreLast)
    {
        InventoryEventHandler.INSTANCE.hotbar(getMainHandStack().getItem(), selectedSlot, ignoreLast);
    }
}

@Mixin(MinecraftClient.class)
abstract class HotbarClientMixin
{
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Inject(at = @At(value = "FIELD", shift = At.Shift.AFTER, target = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I"), method = "handleInputEvents")
    private void hotbarKeyPress(CallbackInfo ci)
    {
        if (player != null)
            InventoryEventHandler.INSTANCE.hotbar(player.getInventory().getMainHandStack().getItem(),
                                                  player.getInventory().selectedSlot);
    }
}