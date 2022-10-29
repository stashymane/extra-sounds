package dev.stashy.extrasounds.sounds

import net.minecraft.client.sound.Sound
import net.minecraft.client.sound.Sound.RegistrationType
import net.minecraft.client.sound.SoundEntry
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.math.floatprovider.ConstantFloatProvider


object Sounds : SoundRegistry {
    val CHAT: SoundEvent = register("chat.message")
    val CHAT_MENTION: SoundEvent = register("chat.mention")
    val HOTBAR_SCROLL: SoundEvent = register("hotbar_scroll")
    val INVENTORY_OPEN: SoundEvent = register("inventory.open")
    val INVENTORY_CLOSE: SoundEvent = register("inventory.close")
    val INVENTORY_SCROLL: SoundEvent = register("inventory.scroll")
    val ITEM_DROP: SoundEvent = register("item.drop")
    val ITEM_PICK: SoundEvent = register("item.pickup")
    val ITEM_PICK_ALL: SoundEvent = register("item.pickup_all")
    val ITEM_CLONE: SoundEvent = register("item.clone")
    val ITEM_DELETE: SoundEvent = register("item.delete")
    val ITEM_DRAG: SoundEvent = register("item.drag")
    val EFFECT_ADD_POSITIVE: SoundEvent = register("effect.add.positive")
    val EFFECT_ADD_NEGATIVE: SoundEvent = register("effect.add.negative")
    val EFFECT_REMOVE_POSITIVE: SoundEvent = register("effect.remove.positive")
    val EFFECT_REMOVE_NEGATIVE: SoundEvent = register("effect.remove.negative")
    val KEYBOARD_TYPE: SoundEvent = register("keyboard.type")

    object Actions {
        val BOW_PULL: SoundEvent = register("action.bow")
        val REPEATER_ADD: SoundEvent = register("action.repeater.add")
        val REPEATER_RESET: SoundEvent = register("action.repeater.reset")
    }

    fun aliased(e: SoundEvent): SoundEntry {
        return aliased(e, 1f)
    }

    fun aliased(e: SoundEvent, volume: Float): SoundEntry {
        return single(e.id, volume, 1f, RegistrationType.SOUND_EVENT)
    }

    fun event(id: Identifier): SoundEntry {
        return event(id, 0.6f)
    }

    fun event(id: Identifier, volume: Float): SoundEntry {
        return single(id, volume, 1.7f, RegistrationType.SOUND_EVENT)
    }

    fun single(id: Identifier, volume: Float, pitch: Float, type: RegistrationType?): SoundEntry {
        return SoundEntry(
            listOf(
                Sound(
                    id.toString(), ConstantFloatProvider.create(volume), ConstantFloatProvider.create(pitch), 1,
                    type, false, false, 16
                )
            ), false, null
        )
    }
}