package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class InventoryClickSounds
{

    @Shadow
    @Final
    protected PlayerInventory playerInventory;

    @Inject(at = @At("INVOKE"), method = "onMouseClick")
    void click(Slot slot, int invSlot, int clickData, SlotActionType actionType, CallbackInfo ci)
    {
        System.out.println("Clicked: " + invSlot);
        if (slot != null)
            ExtraSounds.inventoryClick(slot.getStack(), playerInventory.getCursorStack(), actionType);
    }
}
