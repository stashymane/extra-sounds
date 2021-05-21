package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SnowballItem;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SnowballItem.class)
public class SnowballItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.BLOCK_SNOW_PLACE, 1f);

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
