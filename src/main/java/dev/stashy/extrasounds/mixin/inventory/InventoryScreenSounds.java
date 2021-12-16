package dev.stashy.extrasounds.mixin.inventory;

import dev.stashy.extrasounds.ExtraSounds;
import dev.stashy.extrasounds.Mixers;
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
        if (ExtraSounds.config.enableInventoryOpeningSounds)
        {
            ExtraSounds.playSound(Sounds.INVENTORY_OPEN, Mixers.INTERFACE);
        }

    }

    @Inject(at = @At("HEAD"), method = "onClose")
    void close(CallbackInfo ci)
    {
        if (ExtraSounds.config.enableInventoryOpeningSounds)
        {
            ExtraSounds.playSound(Sounds.INVENTORY_CLOSE, Mixers.INTERFACE);
        }
    }
}
