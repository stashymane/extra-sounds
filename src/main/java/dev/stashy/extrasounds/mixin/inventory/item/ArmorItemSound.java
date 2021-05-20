package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ArmorItem.class)
public abstract class ArmorItemSound implements ItemSoundContainer
{
    @Shadow
    @Final
    protected ArmorMaterial type;

    InventorySound snd;

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }

    @Override
    public void initSound(Identifier id)
    {
        snd = new InventorySound(type.getEquipSound(), 1f);
    }
}
