package dev.stashy.extrasounds.mixin.inventory;

import dev.stashy.extrasounds.Mixers;
import dev.stashy.extrasounds.SoundManager;
import dev.stashy.extrasounds.sounds.Sounds;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class InventoryScreenSounds
{
    @Inject(at = @At("HEAD"), method = "init")
    void open(CallbackInfo ci)
    {
        SoundManager.playSound(Sounds.INVENTORY_OPEN, 1f, Mixers.INVENTORY);
    }

    @Inject(at = @At("HEAD"), method = "close")
    void close(CallbackInfo ci)
    {
        SoundManager.playSound(Sounds.INVENTORY_CLOSE, 1f, Mixers.INVENTORY);
    }
}
