package dev.stashy.extrasounds.mixin.inventory;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryClickSounds
        extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler>
{
    public CreativeInventoryClickSounds(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text)
    {
        super(screenHandler, playerInventory, text);
    }

    @Shadow
    @Nullable
    private Slot deleteItemSlot;

    @Shadow
    private static int selectedTab;

    @Inject(at = @At("INVOKE"), method = "onMouseClick")
    void slotClick(Slot slot, int invSlot, int clickData, SlotActionType actionType, CallbackInfo ci)
    {
        if (slot == null || client == null || client.player == null) return;

        if (slot == deleteItemSlot && selectedTab == ItemGroup.INVENTORY.getIndex())
            ExtraSounds.playSound(ExtraSounds.config.itemDelete);
        else
            ExtraSounds.inventoryClick(slot.getStack(), client.player.inventory.getCursorStack(),
                                       actionType);
    }

    @Inject(at = @At("INVOKE"), method = "setSelectedTab")
    void tabChange(ItemGroup group, CallbackInfo ci)
    {
        if (selectedTab != -1 && group.getIndex() != selectedTab)
            ExtraSounds.playItemSound(group.getIcon(), true);
    }
}
