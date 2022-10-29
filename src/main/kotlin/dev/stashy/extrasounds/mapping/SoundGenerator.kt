package dev.stashy.extrasounds.mapping

import net.minecraft.item.Item

open class SoundGenerator(val namespace: String, val modId: String, val generator: (Item) -> ItemSound)