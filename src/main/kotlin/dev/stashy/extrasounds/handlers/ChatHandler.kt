package dev.stashy.extrasounds.handlers

import dev.stashy.extrasounds.SoundManager
import dev.stashy.extrasounds.sounds.SoundType
import dev.stashy.extrasounds.sounds.Sounds
import net.minecraft.client.MinecraftClient
import java.util.*

object ChatHandler {
    fun chatMessage(msg: String, sender: UUID) {
        val player = MinecraftClient.getInstance().player ?: return
        if (player.uuid != sender && (msg.contains(player.name.string) || msg.contains(player.displayName.string))) {
            SoundManager.playSound(Sounds.CHAT_MENTION, SoundType.CHAT)
        } else SoundManager.playSound(Sounds.CHAT, SoundType.CHAT)
    }
}