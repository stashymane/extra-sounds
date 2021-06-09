package dev.stashy.extrasounds.mixin.inventory;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class InventoryClickSounds<T extends ScreenHandler> extends Screen implements ScreenHandlerProvider<T>
{

    @Shadow
    @Final
    protected T handler;

    protected InventoryClickSounds(Text title)
    {
        super(title);
    }

    @Inject(at = @At("INVOKE"), method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V")
    void click(Slot slot, int invSlot, int clickData, SlotActionType actionType, CallbackInfo ci)
    {
        if (slot != null)
            ExtraSounds.inventoryClick(slot, handler.getCursorStack(), actionType);
    }
}
