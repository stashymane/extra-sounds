package dev.stashy.extrasounds.mixin;

import dev.stashy.extrasounds.ExtraSounds;
import dev.stashy.extrasounds.SoundConfig;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
public class ListScroll
{
    private static int lastPos = 0;
    private static long lastTime = 0L;
    private static final SoundConfig.SoundSource src = ExtraSounds.config.listScroll;

    //@Inject(at = @At("HEAD"), method = "scrollItems")
    @ModifyVariable(method = "scrollItems", at = @At("STORE"), ordinal = 1)
    int scroll(int position)
    {
        long now = System.currentTimeMillis();
        long timeDiff = now - lastTime;
        if (timeDiff > 5 && lastPos != position && !(lastPos != 1 && position == 0))
        {
            ExtraSounds.playSound(
                    src,
                    src.pitch - src.pitchRange / 2 + src.pitchRange * Math.min(1, 50f / timeDiff));
            lastTime = now;
            lastPos = position;
        }
        return position;
    }
}
