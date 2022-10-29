package dev.stashy.extrasounds.mapping

import net.minecraft.client.sound.SoundEntry

data class ItemSound(val pickup: SoundEntry, val place: SoundEntry = pickup, val hotbar: SoundEntry = pickup)