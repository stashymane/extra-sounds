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
import net.minecraft.util.registry.Registry
import kotlin.jvm.optionals.getOrNull


@OptIn(ExperimentalStdlibApi::class)
object VanillaGenerator {
    private val map: MutableMap<Class<out Item>, (Item) -> SoundEntry> = mutableMapOf()

    @JvmField
    val generator: SoundGenerator = SoundGenerator("minecraft", MODID) {
        val id = Registry.ITEM.getId(it)
        var cls: Class<out Item> = it.javaClass
        while (!map.containsKey(cls) && cls.superclass != null && Item::class.java.isAssignableFrom(cls.superclass))
            cls = cls.superclass as Class<out Item>
        return@SoundGenerator ItemSound(map[cls]?.invoke(it) ?: aliased(ITEM_PICK))
    }

    init {
        map[MusicDiscItem::class.java] = { aliased(MUSIC_DISC) }
        map[BoatItem::class.java] = { aliased(BOAT) }
        map[ToolItem::class.java] = {
            if ((it as ToolItem).material is ToolMaterials)
                when (it.material) {
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
        map[ArmorItem::class.java] = {
            if ((it as ArmorItem).material is ArmorMaterials) {
                when (it.material) {
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
        map[ShieldItem::class.java] = { aliased(Categories.Gear.IRON) }
        putMulti(
            { aliased(Categories.Gear.GOLDEN) },
            HorseArmorItem::class.java,
            CompassItem::class.java,
            SpyglassItem::class.java,
            ShearsItem::class.java
        )
        putMulti(
            { aliased(Categories.Gear.LEATHER) },
            LeadItem::class.java,
            ElytraItem::class.java,
            SaddleItem::class.java
        )
        putMulti(
            { aliased(Categories.Gear.GENERIC) },
            BowItem::class.java,
            CrossbowItem::class.java,
            FishingRodItem::class.java,
            OnAStickItem::class.java
        )
        map[BucketItem::class.java] = {
            (it as BucketAccessor).fluid.bucketFillSound.getOrNull()?.let { event(it.id, 0.4f) } ?: aliased(METAL)
        }
        map[MinecartItem::class.java] = { aliased(MINECART) }
        map[ItemFrameItem::class.java] = { aliased(FRAME) }
        putMulti({ aliased(POTION) }, PotionItem::class.java, ExperienceBottleItem::class.java)
        putMulti(
            { aliased(PAPER) },
            BannerPatternItem::class.java,
            BookItem::class.java,
            WritableBookItem::class.java,
            WrittenBookItem::class.java,
            EnchantedBookItem::class.java,
            EmptyMapItem::class.java,
            FilledMapItem::class.java,
            NameTagItem::class.java
        )
        map[ArrowItem::class.java] = { aliased(ARROW) }
        map[DyeItem::class.java] = { aliased(DUST) }
        map[SpawnEggItem::class.java] = { aliased(WET_SLIPPERY) }
        putMulti({ aliased(BOWL) }, StewItem::class.java, SuspiciousStewItem::class.java)
        map[GoatHornItem::class.java] = { single(LOOSE_METAL.id, 0.6f, 0.9f, Sound.RegistrationType.SOUND_EVENT) }
        map[BlockItem::class.java] = {
            val b = (it as BlockItem).block
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
    }

    @SafeVarargs
    private fun putMulti(entry: (Item) -> SoundEntry, vararg classez: Class<out Item?>) {
        for (clazz in classez) map[clazz] = entry
    }
}