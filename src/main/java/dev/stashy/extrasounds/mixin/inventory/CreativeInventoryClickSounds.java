package dev.stashy.extrasounds.mixin.inventory;

import dev.stashy.extrasounds.ExtraSounds;
import dev.stashy.extrasounds.SoundManager;
import dev.stashy.extrasounds.sounds.SoundType;
import dev.stashy.extrasounds.sounds.Sounds;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryClickSounds
        extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler>
{

    @Shadow
    private static int selectedTab;

    @Shadow
    @Nullable
    private Slot deleteItemSlot;

    @Shadow
    @Nullable
    private List<Slot> slots;

    public CreativeInventoryClickSounds(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text)
    {
        super(screenHandler, playerInventory, text);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;increment(I)V"), method = "onMouseClick")
    void increment(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci)
    {
        if (slotId >= 0)
            ExtraSounds.inventoryClick(slot, handler.getCursorStack(), actionType);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen$CreativeScreenHandler;setCursorStack(Lnet/minecraft/item/ItemStack;)V"), method = "onMouseClick")
    void click(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci)
    {
        if (slot == deleteItemSlot && !handler.getCursorStack().isEmpty())
            SoundManager.playSound(Sounds.ITEM_DELETE, SoundType.PICKUP);
        else if (slotId >= 0)
            ExtraSounds.inventoryClick(slot, handler.getCursorStack(), actionType);
    }

    @Inject(at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;clickCreativeStack(Lnet/minecraft/item/ItemStack;I)V"), method = "onMouseClick")
    void deleteAll(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci)
    {
        if (slots != null && slots.stream().anyMatch(Slot::hasStack))
            SoundManager.playSound(Sounds.ITEM_DELETE, SoundType.PICKUP);
    }

    @Inject(at = @At("HEAD"), method = "setSelectedTab")
    void tabChange(ItemGroup group, CallbackInfo ci)
    {
        if (selectedTab != -1 && group.getIndex() != selectedTab)
            SoundManager.playSound(group.getIcon(), SoundType.PICKUP);
    }
}

@Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
class CreativeScreenHandlerSounds
{
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;setStack(Lnet/minecraft/item/ItemStack;)V"), method = "transferSlot")
    void transfer(PlayerEntity player, int index, CallbackInfoReturnable<ItemStack> cir)
    {
        SoundManager.playSound(Sounds.ITEM_DELETE, SoundType.PICKUP);
    }
}