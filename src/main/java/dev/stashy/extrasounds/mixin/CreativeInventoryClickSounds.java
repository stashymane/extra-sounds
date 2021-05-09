package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryClickSounds
{
    @Shadow
    public abstract int getSelectedTab();

    @Shadow
    protected abstract boolean isCreativeInventorySlot(@Nullable Slot slot);

    @Inject(at = @At("RETURN"), method = "onMouseClick")
    void slotClick(Slot slot, int invSlot, int clickData, SlotActionType actionType, CallbackInfo ci)
    {
        if (MinecraftClient.getInstance().player == null || slot == null || !isCreativeInventorySlot(slot))
            return;
        ExtraSounds.inventoryClick(slot.getStack(), MinecraftClient.getInstance().player.inventory.getCursorStack(),
                                   actionType);
    }

    @Inject(at = @At("INVOKE"), method = "setSelectedTab")
    void tabChange(ItemGroup group, CallbackInfo ci)
    {
        if (getSelectedTab() != -1 && group.getIndex() != getSelectedTab())
            ExtraSounds.playItemSound(group.getIcon(), true);
    }
}
