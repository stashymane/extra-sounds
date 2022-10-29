package dev.stashy.extrasounds

import dev.stashy.extrasounds.Main.Companion.getClickId
import dev.stashy.extrasounds.sounds.SoundType
import dev.stashy.extrasounds.sounds.Sounds
import net.minecraft.client.MinecraftClient
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.client.sound.SoundInstance
import net.minecraft.item.Item
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager

object SoundManager {
    private var lastPlayed = 0L

    fun playItemSound(item: Item, type: SoundType) {
        playSound(getItemSound(item, type), type)
    }

    fun getItemSound(item: Item, type: SoundType): SoundEvent {
        val id = getClickId(Registry.ITEM.getId(item), type)
        if (!Identifier.isValid(id)) {
            LogManager.getLogger().error("Sound ID is not valid: \"$id\", returning default")
            return Sounds.ITEM_PICK
        }
        return SoundEvent(Identifier.tryParse(id))
    }

    @JvmOverloads
    fun playSound(
        snd: SoundEvent,
        type: SoundType,
        pitch: Float = type.pitch,
        category: SoundCategory = type.category,
        pos: Triple<Double, Double, Double> = Triple(0.0, 0.0, 0.0),
        relative: Boolean = true,
    ) {
        playSound(
            PositionedSoundInstance(
                snd.id, category, getMasterVol(), pitch,
                SoundInstance.createRandom(), false, 0,
                SoundInstance.AttenuationType.NONE, pos.first, pos.second, pos.third, relative
            )
        )
    }

    fun playSound(sound: SoundInstance) {
        throttle({
            MinecraftClient.getInstance().apply {
                send { soundManager.play(sound) }
            }
        })
    }

    fun stopSound(evt: SoundEvent, type: SoundType) {
        MinecraftClient.getInstance().soundManager.stopSounds(evt.id, type.category)
    }

    fun throttle(fn: () -> Unit, interval: Long = 5L) {
        val now = System.currentTimeMillis()
        if (now - lastPlayed > interval) {
            fn()
            lastPlayed = now
        }
    }

    private fun getMasterVol(): Float =
        MinecraftClient.getInstance().options.getSoundVolume(Mixers.Master)

}