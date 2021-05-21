package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({BookItem.class, EnchantedBookItem.class, NameTagItem.class, WritableBookItem.class, EmptyMapItem.class, FilledMapItem.class, BannerPatternItem.class})
public class DefaultPaperItemSound implements ItemSoundContainer
{
    InventorySound snd = new InventorySound(SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN, 1f);;

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }
}
