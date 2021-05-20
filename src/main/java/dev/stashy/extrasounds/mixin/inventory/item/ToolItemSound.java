package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ToolItem.class)
public class ToolItemSound implements ItemSoundContainer
{
    @Shadow
    @Final
    private ToolMaterial material;
    InventorySound snd;

    @Override
    public InventorySound getInventorySound()
    {
        return snd;
    }

    @Override
    public void initSound(Identifier id)
    {
        SoundEvent e;
        if (material.equals(ToolMaterials.WOOD))
            e = SoundEvents.BLOCK_WOOD_PLACE;
        else if (material.equals(ToolMaterials.IRON))
            e = SoundEvents.ITEM_ARMOR_EQUIP_IRON;
        else if (material.equals(ToolMaterials.GOLD))
            e = SoundEvents.ITEM_ARMOR_EQUIP_GOLD;
        else if (material.equals(ToolMaterials.DIAMOND))
            e = SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
        else if (material.equals(ToolMaterials.NETHERITE))
            e = SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
        else
            e = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
        snd = new InventorySound(e, 1f);
    }
}
