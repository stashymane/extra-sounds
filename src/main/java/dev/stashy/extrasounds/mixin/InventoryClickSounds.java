package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ScreenHandler.class)
public class InventoryClickSounds
{
    @Shadow
    @Final
    public List<Slot> slots;

    @Inject(at = @At("HEAD"), method = "onSlotClick")
    void click(int i, int j, SlotActionType actionType, PlayerEntity playerEntity, CallbackInfoReturnable<ItemStack> cir)
    {
        switch (actionType)
        {
            case PICKUP:
                if (!playerEntity.inventory.getCursorStack().isEmpty())
                {
                    ExtraSounds.playSound(ExtraSounds.config.itemPlace);
                    return;
                }
                else if (this.slots.get(i) == null || !this.slots.get(i).hasStack())
                    return;
            case SWAP:
                ExtraSounds.playSound(ExtraSounds.config.itemPickup);
                return;
            case QUICK_MOVE:
                if (this.slots.get(i) == null || !this.slots.get(i).hasStack())
                    ExtraSounds.playSound(ExtraSounds.config.itemTransfer);
                return;
            case PICKUP_ALL:
                if (!playerEntity.inventory.getCursorStack().isEmpty())
                    ExtraSounds.playSound(ExtraSounds.config.itemPickupAll);
                return;
            case CLONE:
                if (this.slots.get(i) == null || !this.slots.get(i).hasStack())
                    ExtraSounds.playSound(ExtraSounds.config.itemClone);
                return;
            case QUICK_CRAFT:
                ExtraSounds.playSound(ExtraSounds.config.itemPlace);
                return;
            default:
        }
    }
}
