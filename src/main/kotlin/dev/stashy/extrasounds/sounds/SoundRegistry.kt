package dev.stashy.extrasounds.sounds

import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier

interface SoundRegistry {
    fun register(id: String): SoundEvent {
        return SoundEvent(Identifier("extrasounds:${id}"))
    }
}