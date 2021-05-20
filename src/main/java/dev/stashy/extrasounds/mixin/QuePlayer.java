package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/*
    this seems like a gross hack to play sounds,
    however it's the only way I got the sounds to play without causing a concurrentmodificationexception :)
 */
@Mixin(SoundSystem.class)
public class QuePlayer
{
    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V", shift = At.Shift.AFTER))
    private void tick(CallbackInfo ci)
    {
        List<PositionedSoundInstance> q = ExtraSounds.que;
        for (PositionedSoundInstance positionedSoundInstance : q)
            ((SoundSystem) (Object) this).play(positionedSoundInstance);
        ExtraSounds.que.clear();
    }
}
