package dev.stashy.extrasounds.mixin.categories;

import dev.stashy.extrasounds.ui.SoundMixerGuiDescription;
import dev.stashy.extrasounds.ui.SoundMixerScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OptionsScreen.class)
public class SoundMixerGuiMixin
{
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"), method = "method_19829")
    //soundOptionsScreen lambda
    public void getSoundOptionsScreen(MinecraftClient instance, Screen screen)
    {
        instance.setScreen(new SoundMixerScreen(new SoundMixerGuiDescription((OptionsScreen) (Object) this)));
    }
}
