package dev.stashy.extrasounds.mixin.inventory.item;

import dev.stashy.extrasounds.InventorySound;
import dev.stashy.extrasounds.ItemSoundContainer;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;

@Mixin(Item.class)
public abstract class BaseItemSound implements ItemSoundContainer
{
    @Shadow
    @Final
    @Nullable
    private FoodComponent foodComponent;

    public InventorySound invSound = new InventorySound(SoundEvents.BLOCK_GLASS_PLACE, 1f);

    private static final HashMap<String, SoundEvent> idMap = new HashMap<>();
    private static final HashMap<FoodComponent, SoundEvent> foodMap = new HashMap<>();

    @Override
    public void initSound(Identifier id)
    {
        SoundEvent snd = null;
        float baseVol = 1f;
        if (idMap.containsKey(id.getPath()))
            snd = idMap.get(id.getPath());
        else if (foodComponent != null)
        {
            if (foodMap.containsKey(foodComponent))
                snd = foodMap.get(foodComponent);
            else if (foodComponent.isMeat())
                snd = SoundEvents.ENTITY_COD_FLOP;
            else
            {
                snd = SoundEvents.ENTITY_CHICKEN_STEP;
                baseVol = 1.5f;
            }
        }

        if (snd != null) invSound = new InventorySound(snd, baseVol);
    }

    static
    {
        idMap.put("slime_ball", SoundEvents.BLOCK_SLIME_BLOCK_BREAK);
        idMap.put("bone", SoundEvents.BLOCK_BONE_BLOCK_HIT);
        idMap.put("clay_ball", SoundEvents.BLOCK_GRAVEL_PLACE);
        idMap.put("gunpowder", SoundEvents.BLOCK_SAND_PLACE);
        idMap.put("sugar", SoundEvents.BLOCK_SAND_PLACE);
        idMap.put("wheat", SoundEvents.BLOCK_SAND_PLACE);
        idMap.put("blaze_powder", SoundEvents.BLOCK_SAND_PLACE);
        idMap.put("glowstone_dust", SoundEvents.BLOCK_SAND_PLACE);
        idMap.put("feather", SoundEvents.BLOCK_SAND_PLACE);
        idMap.put("leather_horse_armor", SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
        idMap.put("iron_horse_armor", SoundEvents.ITEM_ARMOR_EQUIP_IRON);
        idMap.put("golden_horse_armor", SoundEvents.ITEM_ARMOR_EQUIP_GOLD);
        idMap.put("diamond_horse_armor", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND);
        idMap.put("paper", SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN);
        idMap.put("honeycomb", SoundEvents.BLOCK_HONEY_BLOCK_PLACE);
        idMap.put("dragon_breath", SoundEvents.ITEM_BOTTLE_FILL);
        idMap.put("stick", SoundEvents.BLOCK_WOOD_PLACE);
        idMap.put("bowl", SoundEvents.BLOCK_WOOD_PLACE);
        idMap.put("fermented_spider_eye", SoundEvents.BLOCK_WEEPING_VINES_PLACE);
        idMap.put("phantom_membrane", SoundEvents.BLOCK_WEEPING_VINES_PLACE);
        idMap.put("magma_cream", SoundEvents.BLOCK_WEEPING_VINES_PLACE);
        idMap.put("ink_sac", SoundEvents.BLOCK_WEEPING_VINES_PLACE);
        idMap.put("iron_ingot", SoundEvents.BLOCK_NETHER_GOLD_ORE_HIT);
        idMap.put("iron_nugget", SoundEvents.BLOCK_NETHER_GOLD_ORE_HIT);
        idMap.put("gold_ingot", SoundEvents.BLOCK_NETHER_GOLD_ORE_HIT);
        idMap.put("gold_nugget", SoundEvents.BLOCK_NETHER_GOLD_ORE_HIT);
        idMap.put("netherite_ingot", SoundEvents.BLOCK_NETHERITE_BLOCK_HIT);
        idMap.put("netherite_scrap", SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE);
        idMap.put("leather", SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
        idMap.put("rabbit_hide", SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
        idMap.put("scute", SoundEvents.ENTITY_COD_FLOP);
        idMap.put("rabbit_foot", SoundEvents.ENTITY_COD_FLOP);
        idMap.put("glistering_melon_slice", SoundEvents.ENTITY_COD_FLOP);
        idMap.put("ghast_tear", SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP);
        idMap.put("painting", SoundEvents.BLOCK_WOOD_PLACE);
        idMap.put("clock", SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);

        foodMap.put(FoodComponents.COD, SoundEvents.ENTITY_COD_FLOP);
        foodMap.put(FoodComponents.COOKED_COD, SoundEvents.ENTITY_COD_FLOP);
        foodMap.put(FoodComponents.SALMON, SoundEvents.ENTITY_COD_FLOP);
        foodMap.put(FoodComponents.COOKED_SALMON, SoundEvents.ENTITY_COD_FLOP);
        foodMap.put(FoodComponents.TROPICAL_FISH, SoundEvents.ENTITY_COD_FLOP);
        foodMap.put(FoodComponents.PUFFERFISH, SoundEvents.ENTITY_COD_FLOP);
        foodMap.put(FoodComponents.BREAD, SoundEvents.BLOCK_WOOL_PLACE);
        foodMap.put(FoodComponents.APPLE, SoundEvents.BLOCK_CORAL_BLOCK_PLACE);
        foodMap.put(FoodComponents.GOLDEN_APPLE, SoundEvents.BLOCK_CORAL_BLOCK_PLACE);
        foodMap.put(FoodComponents.ENCHANTED_GOLDEN_APPLE, SoundEvents.BLOCK_CORAL_BLOCK_PLACE);
        foodMap.put(FoodComponents.BAKED_POTATO, SoundEvents.BLOCK_NETHER_SPROUTS_PLACE);
        foodMap.put(FoodComponents.GOLDEN_CARROT, SoundEvents.BLOCK_NETHER_SPROUTS_PLACE);
        foodMap.put(FoodComponents.POISONOUS_POTATO, SoundEvents.ITEM_NETHER_WART_PLANT);
        foodMap.put(FoodComponents.DRIED_KELP, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE);
        foodMap.put(FoodComponents.COOKIE, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE);
        foodMap.put(FoodComponents.BEETROOT, SoundEvents.BLOCK_CORAL_BLOCK_PLACE);
        foodMap.put(FoodComponents.MELON_SLICE, SoundEvents.BLOCK_WET_GRASS_PLACE);
        foodMap.put(FoodComponents.PUMPKIN_PIE, SoundEvents.BLOCK_LILY_PAD_PLACE);

    }

    @Override
    public InventorySound getInventorySound()
    {
        return invSound;
    }
}
