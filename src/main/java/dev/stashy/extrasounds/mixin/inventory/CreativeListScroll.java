package dev.stashy.extrasounds.mixin.inventory;

import dev.stashy.extrasounds.ExtraSounds;
import dev.stashy.extrasounds.Sounds;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CreativeInventoryScreen.CreativeScreenHandler.class)
public class CreativeListScroll
{
    private static int lastPos = 0;
    private static long lastTime = 0L;
    private static final SoundEvent e = Sounds.INVENTORY_SCROLL;

    @ModifyVariable(method = "scrollItems", at = @At("STORE"), ordinal = 1)
    int scroll(int position)
    {
        long now = System.currentTimeMillis();
        long timeDiff = now - lastTime;
        if (timeDiff > 20 && lastPos != position && !(lastPos != 1 && position == 0))
        {
            ExtraSounds.playSound(
                    e,
                    (1f - 0.1f + 0.1f * Math.min(1, 50f / timeDiff)));
            lastTime = now;
            lastPos = position;
        }
        return position;
    }
}
