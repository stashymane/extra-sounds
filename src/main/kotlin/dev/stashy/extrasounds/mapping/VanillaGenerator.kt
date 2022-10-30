package dev.stashy.extrasounds.mapping

import dev.stashy.extrasounds.MODID
import dev.stashy.extrasounds.mixin.accessors.BlockAccessor
import dev.stashy.extrasounds.mixin.accessors.BucketAccessor
import dev.stashy.extrasounds.sounds.Categories
import dev.stashy.extrasounds.sounds.Categories.ARROW
import dev.stashy.extrasounds.sounds.Categories.BANNER
import dev.stashy.extrasounds.sounds.Categories.BOAT
import dev.stashy.extrasounds.sounds.Categories.BOWL
import dev.stashy.extrasounds.sounds.Categories.DUST
import dev.stashy.extrasounds.sounds.Categories.FRAME
import dev.stashy.extrasounds.sounds.Categories.LEAVES
import dev.stashy.extrasounds.sounds.Categories.LOOSE_METAL
import dev.stashy.extrasounds.sounds.Categories.METAL
import dev.stashy.extrasounds.sounds.Categories.MINECART
import dev.stashy.extrasounds.sounds.Categories.MUSIC_DISC
import dev.stashy.extrasounds.sounds.Categories.PAPER
import dev.stashy.extrasounds.sounds.Categories.POTION
import dev.stashy.extrasounds.sounds.Categories.RAIL
import dev.stashy.extrasounds.sounds.Categories.WET_SLIPPERY
import dev.stashy.extrasounds.sounds.Sounds.ITEM_PICK
import dev.stashy.extrasounds.sounds.Sounds.aliased
import dev.stashy.extrasounds.sounds.Sounds.event
import dev.stashy.extrasounds.sounds.Sounds.single
import net.minecraft.block.*
import net.minecraft.client.sound.Sound
import net.minecraft.client.sound.SoundEntry
import net.minecraft.item.*
import kotlin.jvm.optionals.getOrNull


@OptIn(ExperimentalStdlibApi::class)
object VanillaGenerator {
    @JvmField
    val generator: SoundGenerator = SoundGenerator("minecraft", MODID) {
        ItemSound(parse(it))
    }

    fun parse(item: Item): SoundEntry = when (item) {
        is MusicDiscItem -> {
            aliased(MUSIC_DISC)
        }

        is BoatItem -> {
            aliased(BOAT)
        }

        is ToolItem -> {
            if (item.material is ToolMaterials)
                when (item.material) {
                    ToolMaterials.WOOD -> aliased(Categories.Gear.WOOD)
                    ToolMaterials.STONE -> aliased(Categories.Gear.STONE)
                    ToolMaterials.IRON -> aliased(Categories.Gear.IRON)
                    ToolMaterials.GOLD -> aliased(Categories.Gear.GOLDEN)
                    ToolMaterials.DIAMOND -> aliased(Categories.Gear.DIAMOND)
                    ToolMaterials.NETHERITE -> aliased(Categories.Gear.NETHERITE)
                    else -> aliased(Categories.Gear.GENERIC)
                }
            else aliased(Categories.Gear.GENERIC)
        }

        is ArmorItem -> {
            if (item.material is ArmorMaterials) {
                when (item.material) {
                    ArmorMaterials.IRON -> aliased(Categories.Gear.IRON)
                    ArmorMaterials.GOLD -> aliased(Categories.Gear.GOLDEN)
                    ArmorMaterials.DIAMOND -> aliased(Categories.Gear.DIAMOND)
                    ArmorMaterials.NETHERITE -> aliased(Categories.Gear.NETHERITE)
                    ArmorMaterials.CHAIN -> aliased(Categories.Gear.CHAIN)
                    ArmorMaterials.TURTLE -> aliased(Categories.Gear.TURTLE)
                    ArmorMaterials.LEATHER -> aliased(Categories.Gear.LEATHER)
                    else -> aliased(Categories.Gear.GENERIC)
                }
            } else aliased(Categories.Gear.GENERIC)
        }

        is ShieldItem -> {
            aliased(Categories.Gear.IRON)
        }

        is HorseArmorItem, is CompassItem, is SpyglassItem, is ShearsItem -> {
            aliased(Categories.Gear.GOLDEN)
        }

        is LeadItem, is ElytraItem, is SaddleItem -> {
            aliased(Categories.Gear.LEATHER)
        }

        is BowItem, is CrossbowItem, is FishingRodItem, is OnAStickItem<*> -> {
            aliased(Categories.Gear.GENERIC)
        }

        is BucketItem -> {
            (item as BucketAccessor).fluid.bucketFillSound.getOrNull()?.let { event(it.id, 0.4f) } ?: aliased(METAL)
        }

        is MinecartItem -> {
            aliased(MINECART)
        }

        is ItemFrameItem -> {
            aliased(FRAME)
        }

        is PotionItem, is ExperienceBottleItem -> {
            aliased(POTION)
        }

        is BannerPatternItem, is BookItem, is WritableBookItem, is WrittenBookItem,
        is EnchantedBookItem, is EmptyMapItem, is FilledMapItem, is NameTagItem,
        -> {
            aliased(PAPER)
        }

        is ArrowItem -> {
            aliased(ARROW)
        }

        is DyeItem -> {
            aliased(DUST)
        }

        is SpawnEggItem -> {
            aliased(WET_SLIPPERY)
        }

        is StewItem, is SuspiciousStewItem -> {
            aliased(BOWL)
        }

        is GoatHornItem -> {
            single(LOOSE_METAL.id, 0.6f, 0.9f, Sound.RegistrationType.SOUND_EVENT)
        }

        is BlockItem -> {
            val b = item.block
            val id = b.getSoundGroup(b.defaultState).placeSound.id
            when (b) {
                is AbstractRailBlock -> aliased(RAIL)
                is BannerBlock -> aliased(BANNER)
                is SeaPickleBlock -> event(id, 0.4f)
                is LeavesBlock, is PlantBlock, is SugarCaneBlock ->
                    if (id.path.equals("block.grass.place")) aliased(LEAVES) else event(id)

                is PillarBlock ->
                    if ((b as BlockAccessor).material.equals(Material.FROGLIGHT)) event(id, 0.3f)
                    else event(id)

                else -> event(id)
            }
        }

        else -> {
            aliased(ITEM_PICK)
        }
    }
}