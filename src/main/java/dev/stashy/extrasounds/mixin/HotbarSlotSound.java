package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import dev.stashy.extrasounds.SoundConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MinecraftClient.class)
public class HotbarSlotSound
{
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Shadow
    @Final
    public InGameHud inGameHud;

    @Inject(method = "handleInputEvents", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot*:I"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void hotbarKeyboardSound(CallbackInfo info, int i)
    {
        if (this.player != null && this.player.inventory.selectedSlot != i)
            playScroll();
    }

    @Inject(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SpectatorHud;selectSlot(I)V"))
    private void spectatorHotbarSound(CallbackInfo ci)
    {
        playScroll();
    }

    private void playScroll()
    {
        SoundConfig.SoundSource scroll = ExtraSounds.config.hotbarScroll;
        ExtraSounds.playSound(scroll, scroll.pitch - scroll.pitchRange / 2);
    }
}
