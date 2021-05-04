package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class ScrollSound
{
    @Inject(at = @At("RETURN"), method = "scrollInHotbar")
    private void inventorySound(CallbackInfo info)
    {
        ExtraSounds.playSound(ExtraSounds.config.hotbarScroll);
    }
}