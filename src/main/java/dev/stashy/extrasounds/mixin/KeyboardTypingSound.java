package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import dev.stashy.extrasounds.Mixers;
import dev.stashy.extrasounds.sounds.Sounds;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextFieldWidget.class)
public class KeyboardTypingSound
{
    @Inject(at = @At("RETURN"), method = "charTyped")
    public void type(char chr, int modifiers, CallbackInfoReturnable<Boolean> cir)
    {
        if (cir.getReturnValue())
            ExtraSounds.playSound(Sounds.KEYBOARD_TYPE, Mixers.TYPING);
    }
}
