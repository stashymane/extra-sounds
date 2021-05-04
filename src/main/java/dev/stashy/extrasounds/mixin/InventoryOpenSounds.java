package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(PlayerEntity.class)
public class InventoryOpenSounds
{
    @Inject(at = @At("HEAD"), method = "openHandledScreen")
    void close(NamedScreenHandlerFactory factory, CallbackInfoReturnable<OptionalInt> cir)
    {
        ExtraSounds.playSound(ExtraSounds.config.inventoryOpen);
    }

    @Inject(at = @At("HEAD"), method = "closeHandledScreen")
    void close(CallbackInfo ci)
    {
        ExtraSounds.playSound(ExtraSounds.config.inventoryClose);
    }
}
