package dev.stashy.extrasounds

import dev.stashy.extrasounds.mapping.SoundLoader
import dev.stashy.extrasounds.sounds.SoundType
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

const val MODID = "extrasounds"

@Environment(EnvType.CLIENT)
class Main : ClientModInitializer {
    override fun onInitializeClient() {
        DebugUtils.init()
        SoundLoader.init()
    }

    companion object {
        fun getClickId(id: Identifier, type: SoundType, includeNamespace: Boolean = true): String {
            return (if (includeNamespace) "$MODID:" else "") + type.prefix + "." + id.namespace + "." + id.path
        }
    }
}