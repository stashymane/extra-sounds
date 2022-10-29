package dev.stashy.extrasounds.sounds

import dev.stashy.extrasounds.Mixers
import net.minecraft.sound.SoundCategory

enum class SoundType(val pitch: Float, val category: SoundCategory, val prefix: String) {
    PICKUP(1f, Mixers.Inventory, "item.pickup"),
    PLACE(0.9f, Mixers.Inventory, "item.place"),
    HOTBAR(1f, Mixers.Scroll, "item.select"),
    EFFECT(1f, Mixers.Effects, "effect"),
    CHAT(1f, Mixers.Chat, "ui.chat"),
    CHAT_MENTION(1f, Mixers.ChatMention, "ui.chat"),
    TYPING(1f, Mixers.Typing, "ui.typing"),
    ACTION(1f, Mixers.Action, "action");
}