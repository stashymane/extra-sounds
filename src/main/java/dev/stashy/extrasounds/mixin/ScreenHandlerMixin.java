package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.handlers.InventoryEventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ClickType;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin
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
            InventoryEventHandler.INSTANCE.click(slot.getStack().getItem(), getCursorStack().getItem(),
                                                 SlotActionType.PICKUP);
    }

    @Inject(at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/screen/ScreenHandler;setCursorStack(Lnet/minecraft/item/ItemStack;)V"), method = "internalOnSlotClick")
    void click(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci)
    {
        Item clicked = Items.AIR;
        System.out.println(slotIndex);
        if (slotIndex >= 0)
            clicked = slots.get(slotIndex).getStack().getItem();

        InventoryEventHandler.INSTANCE.click(clicked, getCursorStack().getItem(),
                                             actionType);
    }

    @Inject(at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/item/ItemStack;increment(I)V"), method = "internalOnSlotClick")
    void transferAll(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci)
    {
        if (slotIndex >= 0)
            InventoryEventHandler.INSTANCE.click(slots.get(slotIndex).getStack().getItem(), getCursorStack().getItem(),
                                                 actionType);
    }

    @Inject(at = @At(value = "INVOKE_ASSIGN", ordinal = 0, target = "Lnet/minecraft/screen/ScreenHandler;transferSlot(Lnet/minecraft/entity/player/PlayerEntity;I)Lnet/minecraft/item/ItemStack;"), method = "internalOnSlotClick", locals = LocalCapture.CAPTURE_FAILSOFT)
    void transfer(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci, ClickType clickType, Slot slot, ItemStack itemStack)
    {
        InventoryEventHandler.INSTANCE.click(itemStack.getItem(), getCursorStack().getItem(), actionType);
    }
}

@Mixin(HandledScreen.class)
abstract
class InventoryKeyPressSound<T extends ScreenHandler>
{
    @Shadow
    @Nullable
    protected Slot focusedSlot;

    @Shadow
    public abstract T getScreenHandler();

    @Shadow
    @Final
    protected T handler;

    @Inject(at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V"), method = "handleHotbarKeyPressed")
    void inventoryHandSwap(int keyCode, int scanCode, CallbackInfoReturnable<Boolean> cir)
    {
        if (focusedSlot != null && focusedSlot.hasStack())
            InventoryEventHandler.INSTANCE.click(Items.AIR, focusedSlot.getStack().getItem(), SlotActionType.SWAP);
    }

    @Inject(at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V"), method = "handleHotbarKeyPressed", locals = LocalCapture.CAPTURE_FAILSOFT)
    void inventorySlotSwap(int keyCode, int scanCode, CallbackInfoReturnable<Boolean> cir, int i)
    {
        if (focusedSlot != null && focusedSlot.hasStack())
        {
            InventoryEventHandler.INSTANCE.click(Items.AIR, focusedSlot.getStack().getItem(), SlotActionType.SWAP);
        }
        else if (MinecraftClient.getInstance().player != null)
        {
            var stack = MinecraftClient.getInstance().player.getInventory().main.get(i);
            if (!stack.isEmpty())
                InventoryEventHandler.INSTANCE.click(stack.getItem(), Items.AIR, SlotActionType.SWAP);
        }
    }
}
