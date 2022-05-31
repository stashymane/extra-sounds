package dev.stashy.extrasounds.mixin.inventory;

import dev.stashy.extrasounds.ExtraSounds;
import dev.stashy.extrasounds.SoundManager;
import dev.stashy.extrasounds.sounds.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ClickType;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ScreenHandler.class)
public abstract class InventoryClickSounds
{
    @Shadow
    @Final
    public DefaultedList<Slot> slots;

    @Shadow
    public abstract ItemStack getCursorStack();

    @Inject(at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/screen/ScreenHandler;setCursorStack(Lnet/minecraft/item/ItemStack;)V"), method = "method_34249")
    void pickup(Slot slot, PlayerEntity playerEntity, ItemStack stack, CallbackInfo ci)
    {
        if (!stack.isEmpty())
            SoundManager.playSound(stack, SoundType.PICKUP);
    }

    @Inject(at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/screen/ScreenHandler;setCursorStack(Lnet/minecraft/item/ItemStack;)V"), method = "internalOnSlotClick")
    void click(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci)
    {
        if (slotIndex >= 0)
            ExtraSounds.inventoryClick(slots.get(slotIndex), getCursorStack(), actionType);
    }

    @Inject(at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/item/ItemStack;increment(I)V"), method = "internalOnSlotClick")
    void transferAll(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci)
    {
        if (slotIndex >= 0)
            ExtraSounds.inventoryClick(slots.get(slotIndex), getCursorStack(), actionType);
    }

    @Inject(at = @At(value = "INVOKE_ASSIGN", ordinal = 0, target = "Lnet/minecraft/screen/ScreenHandler;transferSlot(Lnet/minecraft/entity/player/PlayerEntity;I)Lnet/minecraft/item/ItemStack;"), method = "internalOnSlotClick", locals = LocalCapture.CAPTURE_FAILSOFT)
    void transfer(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci, PlayerInventory playerInventory, ClickType clickType, Slot slot, ItemStack itemStack)
    {
        if (!itemStack.isEmpty())
            SoundManager.playSound(itemStack, SoundType.PLACE);
    }
}
