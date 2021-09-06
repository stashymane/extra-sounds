package dev.stashy.extrasounds.mixin.inventory;

import dev.stashy.extrasounds.ExtraSounds;
import dev.stashy.extrasounds.Sounds;
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

import java.util.List;

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

    @Shadow
    @Nullable
    private List<Slot> slots;
    private long lastDeleteSound;

    @Inject(at = @At("INVOKE"), method = "onMouseClick")
    void slotClick(Slot slot, int invSlot, int clickData, SlotActionType actionType, CallbackInfo ci)
    {
        if (slot == null || client == null || client.player == null) return;

        if (System.currentTimeMillis() - lastDeleteSound > 5
                && slot == deleteItemSlot
                && selectedTab == ItemGroup.INVENTORY.getIndex())
        {
            if (actionType.equals(SlotActionType.PICKUP) && handler.getCursorStack().isEmpty()
                    || actionType.equals(SlotActionType.QUICK_MOVE)
                    && slots != null && slots.parallelStream().noneMatch(Slot::hasStack))
                return;
            ExtraSounds.playSound(Sounds.ITEM_DELETE);
            lastDeleteSound = System.currentTimeMillis();
        }
        else
            ExtraSounds.inventoryClick(slot, handler.getCursorStack(),
                                       actionType);
    }

    @Inject(at = @At("INVOKE"), method = "setSelectedTab")
    void tabChange(ItemGroup group, CallbackInfo ci)
    {
        if (selectedTab != -1 && group.getIndex() != selectedTab)
            ExtraSounds.playItemSound(group.getIcon(), true);
    }
}
