package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenHandler.class)
public abstract class InventoryClickSounds
{
    @Shadow
    public abstract Slot getSlot(int index);

    @Inject(at = @At("RETURN"), method = "onSlotClick")
    void click(int i, int j, SlotActionType actionType, PlayerEntity playerEntity, CallbackInfoReturnable<ItemStack> cir)
    {
        if (i >= 0)
            ExtraSounds.inventoryClick(getSlot(i).getStack(), playerEntity.inventory.getCursorStack(), actionType);
    }
}
