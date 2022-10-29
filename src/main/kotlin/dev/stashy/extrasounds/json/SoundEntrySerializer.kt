package dev.stashy.extrasounds.json

import com.google.gson.*
import net.minecraft.client.sound.SoundEntry
import java.lang.reflect.Type

class SoundEntrySerializer : JsonSerializer<SoundEntry> {
    override fun serialize(src: SoundEntry, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonObject().also { obj ->
            obj.add("sounds", JsonArray().also { arr ->
                src.sounds.forEach { arr.add(context.serialize(it)) }
            })
            if (src.canReplace())
                obj.addProperty("replace", src.canReplace())
            if (src.subtitle != null)
                obj.addProperty("subtitle", src.subtitle)
        }
    }
}