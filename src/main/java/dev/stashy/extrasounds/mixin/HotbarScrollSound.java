package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class HotbarScrollSound
{
    @Shadow
    public int selectedSlot;

    @Inject(at = @At("RETURN"), method = "scrollInHotbar")
    private void hotbarSound(CallbackInfo info)
    {
        ExtraSounds.hotbar(selectedSlot);
    }
}
