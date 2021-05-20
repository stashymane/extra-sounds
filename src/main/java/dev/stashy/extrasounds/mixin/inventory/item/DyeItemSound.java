package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.DyeItem;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DyeItem.class)
public class DyeItemSound implements ItemSoundContainer
{
    InventorySound snd;

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }

    @Override
    public void initSound(Identifier id)
    {
        snd = new InventorySound(SoundEvents.BLOCK_SAND_PLACE, 1f);
    }
}
