package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class BaseItemSound implements ItemSoundContainer
{}
