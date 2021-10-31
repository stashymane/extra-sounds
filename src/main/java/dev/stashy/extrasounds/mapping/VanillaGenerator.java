package dev.stashy.extrasounds.mapping;

import dev.stashy.extrasounds.mixin.BucketFluidAccessor;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static dev.stashy.extrasounds.sounds.Categories.*;
import static dev.stashy.extrasounds.sounds.Sounds.*;

public class VanillaGenerator
{
    public static final Map<Class<? extends Item>, Function<Item, SoundEntry>> map = new HashMap<>();

    public static SoundGenerator generator = SoundGenerator.of("minecraft", id -> {
        Item i = Registry.ITEM.get(id);
        Class<? extends Item> cls = i.getClass();
        while (!map.containsKey(cls) && cls.getSuperclass() != null && cls.getSuperclass().isAssignableFrom(Item.class))
            cls = (Class<? extends Item>) cls.getSuperclass();
        return map.containsKey(cls) ? map.get(cls).apply(i) : aliased(ITEM_PICK);
    });

    static
    {
        map.put(MusicDiscItem.class, it -> aliased(MUSIC_DISC));
        map.put(BoatItem.class, it -> aliased(BOAT));
        map.put(ToolItem.class, it -> {
            if (((ToolItem) it).getMaterial() instanceof ToolMaterials mat)
                return switch (mat)
                        {
                            case WOOD -> aliased(Gear.WOOD);
                            case STONE -> aliased(Gear.STONE);
                            case IRON -> aliased(Gear.IRON);
                            case GOLD -> aliased(Gear.GOLDEN);
                            case DIAMOND -> aliased(Gear.DIAMOND);
                            case NETHERITE -> aliased(Gear.NETHERITE);
                            default -> aliased(Gear.GENERIC);
                            //⬆ even though not required, this is in case any mods add to the enum of materials
                        };
            else
                return aliased(Gear.GENERIC);
        });
        map.put(ArmorItem.class, it -> {
            if (((ArmorItem) it).getMaterial() instanceof ArmorMaterials mat)
                return switch (mat)
                        {
                            case IRON -> aliased(Gear.IRON);
                            case GOLD -> aliased(Gear.GOLDEN);
                            case DIAMOND -> aliased(Gear.DIAMOND);
                            case NETHERITE -> aliased(Gear.NETHERITE);
                            case CHAIN -> aliased(Gear.CHAIN);
                            case TURTLE -> aliased(Gear.TURTLE);
                            case LEATHER -> aliased(Gear.LEATHER);
                            default -> aliased(Gear.GENERIC);
                        };
            else
                return aliased(Gear.GENERIC);
        });
        map.put(ShieldItem.class, it -> aliased(Gear.IRON));
        putMulti(it -> aliased(Gear.GOLDEN), HorseArmorItem.class, CompassItem.class, SpyglassItem.class,
                 ShearsItem.class);
        putMulti(it -> aliased(Gear.LEATHER), LeadItem.class, ElytraItem.class, SaddleItem.class);
        putMulti(it -> aliased(Gear.GENERIC), BowItem.class, CrossbowItem.class, FishingRodItem.class,
                 OnAStickItem.class);
        map.put(BucketItem.class, it -> {
            var f = ((BucketFluidAccessor) it).getFluid();
            return f.getBucketFillSound().isPresent() ?
                    event(f.getBucketFillSound().get().getId()) : aliased(METAL);
        });
        map.put(MinecartItem.class, it -> aliased(MINECART));
        map.put(ItemFrameItem.class, it -> aliased(FRAME));
        putMulti(it -> aliased(POTION), PotionItem.class, ExperienceBottleItem.class);
        putMulti(it -> aliased(PAPER), BannerPatternItem.class, BookItem.class, WritableBookItem.class,
                 WrittenBookItem.class,
                 EnchantedBookItem.class, EmptyMapItem.class, FilledMapItem.class, NameTagItem.class);
        map.put(ArrowItem.class, it -> aliased(ARROW));
        map.put(DyeItem.class, it -> aliased(DUST));
        map.put(SpawnEggItem.class, it -> aliased(WET_SLIPPERY));
        putMulti(it -> aliased(BOWL), MushroomStewItem.class, SuspiciousStewItem.class);
        map.put(BlockItem.class, it -> {
            Block b = ((BlockItem) it).getBlock();
            if (b instanceof AbstractRailBlock)
                return aliased(RAIL);
            else if (b instanceof BannerBlock)
                return aliased(BANNER);
            else if (b instanceof LeavesBlock)
                return aliased(LEAVES);
            return event(b.getSoundGroup(b.getDefaultState()).getPlaceSound().getId());
        });
    }

    @SafeVarargs
    private static void putMulti(Function<Item, SoundEntry> entry, Class<? extends Item>... classez)
    {
        for (var clazz : classez)
            map.put(clazz, entry);
    }
}
