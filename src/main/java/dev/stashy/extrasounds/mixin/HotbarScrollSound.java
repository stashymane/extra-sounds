package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import dev.stashy.extrasounds.Mixers;
import dev.stashy.extrasounds.sounds.Sounds;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class HotbarScrollSound
{
    @Inject(at = @At("RETURN"), method = "scrollInHotbar")
    private void hotbarSound(CallbackInfo info)
    {
        ExtraSounds.playSound(Sounds.HOTBAR_SCROLL, Mixers.INVENTORY);
    }
}
