package dev.stashy.extrasounds.sounds

import net.minecraft.sound.SoundEvent

object Categories: SoundRegistry {
    val ARROW: SoundEvent = register("item.category.arrow")
    val BANNER: SoundEvent = register("item.category.banner")
    val BOAT: SoundEvent = register("item.category.boat")
    val BOWL: SoundEvent = register("item.category.bowl")
    val CRUNCHY_DRY: SoundEvent = register("item.category.crunchy_dry")
    val DUST: SoundEvent = register("item.category.dust")
    val FOOD_DRY: SoundEvent = register("item.category.food_dry")
    val FRAME: SoundEvent = register("item.category.frame")
    val HAY: SoundEvent = register("item.category.hay")
    val LEAVES: SoundEvent = register("item.category.leaves")
    val LOOSE_METAL: SoundEvent = register("item.category.loose_metal")
    val MEAT: SoundEvent = register("item.category.meat")
    val METAL: SoundEvent = register("item.category.metal")
    val METAL_BITS: SoundEvent = register("item.category.metal_bits")
    val METAL_SHEETS: SoundEvent = register("item.category.metal_sheets")
    val MINECART: SoundEvent = register("item.category.minecart")
    val MUSIC_DISC: SoundEvent = register("item.category.music_disc")
    val PAPER: SoundEvent = register("item.category.paper")
    val POTION: SoundEvent = register("item.category.potion")
    val RAIL: SoundEvent = register("item.category.rail")
    val WET: SoundEvent = register("item.category.wet")
    val WET_SLIPPERY: SoundEvent = register("item.category.wet_slippery")

    object Gear {
        val CHAIN: SoundEvent = register("item.category.gear.chain")
        val DIAMOND: SoundEvent = register("item.category.gear.diamond")
        val GENERIC: SoundEvent = register("item.category.gear.generic")
        val GOLDEN: SoundEvent = register("item.category.gear.golden")
        val IRON: SoundEvent = register("item.category.gear.iron")
        val LEATHER: SoundEvent = register("item.category.gear.leather")
        val NETHERITE: SoundEvent = register("item.category.gear.netherite")
        val STONE: SoundEvent = register("item.category.gear.stone")
        val TURTLE: SoundEvent = register("item.category.gear.turtle")
        val WOOD: SoundEvent = register("item.category.gear.wood")
    }
}