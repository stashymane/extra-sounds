package dev.stashy.extrasounds.mapping;

import dev.stashy.extrasounds.mixin.BucketFluidAccessor;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

import static dev.stashy.extrasounds.sounds.Categories.*;
import static dev.stashy.extrasounds.sounds.Sounds.*;

public class VanillaMapper
{
    static
    {
        //Vanilla mapper
        SoundMapper.register("minecraft", id -> {
            Item i = Registry.ITEM.get(id);
            if (i instanceof BlockItem)
                return block((BlockItem) i);
            else
                return item(i);
        });
    }

    private static SoundEntry item(Item i)
    {
        if (i instanceof MusicDiscItem)
            return aliased(MUSIC_DISC);
        else if (i instanceof BoatItem)
            return aliased(BOAT);
        else if (i instanceof ToolItem t)
        {
            if (t.getMaterial() instanceof ToolMaterials mat)
                return switch (mat)
                        {
                            case WOOD -> aliased(Gear.WOOD);
                            case STONE -> aliased(Gear.STONE);
                            case IRON -> aliased(Gear.IRON);
                            case GOLD -> aliased(Gear.GOLDEN);
                            case DIAMOND -> aliased(Gear.DIAMOND);
                            case NETHERITE -> aliased(Gear.NETHERITE);
                            default -> aliased(Gear.GENERIC);
                        };
            else
                return aliased(Gear.GENERIC);
        }
        else if (i instanceof ArmorItem a)
        {
            if (a.getMaterial() instanceof ArmorMaterials mat)
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
        }
        else if (i instanceof ShieldItem)
            return aliased(Gear.IRON);
        else if (i instanceof HorseArmorItem a || i instanceof CompassItem || i instanceof SpyglassItem || i instanceof ShearsItem)
            return aliased(Gear.GOLDEN);
        else if (i instanceof LeadItem || i instanceof ElytraItem || i instanceof SaddleItem)
            return aliased(Gear.LEATHER);
        else if (i instanceof BowItem || i instanceof CrossbowItem || i instanceof FishingRodItem || i instanceof OnAStickItem)
            return aliased(Gear.GENERIC);
        else if (i instanceof BucketItem b)
        {
            Fluid f = ((BucketFluidAccessor) b).getFluid();
            return f.getBucketFillSound().isPresent() ?
                    event(f.getBucketFillSound().get().getId()) : aliased(METAL);
        }
        else if (i instanceof MinecartItem)
            return aliased(MINECART);
        else if (i instanceof ItemFrameItem)
            return aliased(FRAME);
        else if (i instanceof PotionItem || i instanceof ExperienceBottleItem)
            return aliased(POTION);
        else if (i instanceof BannerPatternItem || i instanceof BookItem || i instanceof WritableBookItem || i instanceof WrittenBookItem
                || i instanceof EnchantedBookItem || i instanceof EmptyMapItem || i instanceof FilledMapItem || i instanceof NameTagItem)
            return aliased(PAPER);
        else if (i instanceof ArrowItem)
            return aliased(ARROW);
        else if (i instanceof DyeItem)
            return aliased(DUST);
        else if (i instanceof SpawnEggItem)
            return aliased(WET_SLIPPERY);
        else if (i instanceof MushroomStewItem || i instanceof SuspiciousStewItem)
            return aliased(BOWL);

        return aliased(ITEM_PICK);
    }

    private static SoundEntry block(BlockItem i)
    {
        Block b = i.getBlock();
        if (b instanceof AbstractRailBlock)
            return aliased(RAIL);
        else if (b instanceof BannerBlock)
            return aliased(BANNER);
        else if (b instanceof LeavesBlock)
            return aliased(LEAVES);
        return event(b.getSoundGroup(b.getDefaultState()).getPlaceSound().getId());
    }
}
