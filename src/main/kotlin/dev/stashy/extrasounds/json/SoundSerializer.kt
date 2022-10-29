package dev.stashy.extrasounds.json

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import net.minecraft.client.sound.Sound
import net.minecraft.client.sound.SoundInstance
import java.lang.reflect.Type

class SoundSerializer : JsonSerializer<Sound> {
    override fun serialize(src: Sound, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val random = SoundInstance.createRandom()
        return JsonObject().apply {
            addProperty("name", src.identifier.toString())
            if (src.volume.get(random) != 1f)
                addProperty("volume", src.volume.get(random))
            if (src.pitch.get(random) != 1f)
                addProperty("pitch", src.pitch.get(random))
            if (src.weight != 1)
                addProperty("weight", src.weight)
            if (src.registrationType != Sound.RegistrationType.FILE)
                addProperty("type", "event")
            if (src.isStreamed)
                addProperty("stream", src.isStreamed)
            if (src.isPreloaded)
                addProperty("preload", src.isPreloaded)
            if (src.attenuation != 16)
                addProperty("attenuation_distance", src.attenuation)
        }
    }
}