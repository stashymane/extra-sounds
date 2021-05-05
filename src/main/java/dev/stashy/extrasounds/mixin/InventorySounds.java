package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class InventorySounds
{
    @Inject(at = @At("HEAD"), method = "init")
    void open(CallbackInfo ci)
    {
        ExtraSounds.playSound(ExtraSounds.config.inventoryOpen);
    }

    @Inject(at = @At("HEAD"), method = "onClose")
    void close(CallbackInfo ci)
    {
        ExtraSounds.playSound(ExtraSounds.config.inventoryClose);
    }
}
