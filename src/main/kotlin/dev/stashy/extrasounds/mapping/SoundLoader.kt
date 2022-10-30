package dev.stashy.extrasounds.mapping

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.stashy.extrasounds.DebugUtils
import dev.stashy.extrasounds.MODID
import dev.stashy.extrasounds.Main.Companion.getClickId
import dev.stashy.extrasounds.json.SoundEntrySerializer
import dev.stashy.extrasounds.json.SoundSerializer
import dev.stashy.extrasounds.sounds.SoundType
import dev.stashy.extrasounds.sounds.Sounds
import net.devtech.arrp.api.RRPCallback
import net.devtech.arrp.api.RuntimeResourcePack
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.sound.Sound
import net.minecraft.client.sound.SoundEntry
import net.minecraft.item.BlockItem
import net.minecraft.resource.ResourceType
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.stream.Collectors


object SoundLoader {
    private const val cacheVersion = 2
    private val genericPack = RuntimeResourcePack.create("extrasounds")
    private val soundsJsonid = Identifier("extrasounds:sounds.json")
    private val cachePath = FabricLoader.getInstance().configDir.resolve("extrasounds.cache")

    private val packs = mutableListOf<RuntimeResourcePack>()
    val mappers = mutableMapOf<String, SoundGenerator>()

    private val gson: Gson =
        GsonBuilder()
            .registerTypeAdapter(SoundEntry::class.java, SoundEntrySerializer())
            .registerTypeAdapter(Sound::class.java, SoundSerializer())
            .create()

    fun init() {
        FabricLoader.getInstance().getEntrypoints(MODID, SoundGenerator::class.java)
            .forEach { mappers[it.namespace] = it }
        val json = getCache() ?: gson.toJson(processSounds()).also { writeCache(it) }
        val jsonBytes = json.toByteArray()

        DebugUtils.exportSoundsJson(jsonBytes)
        DebugUtils.exportGenerators()

        genericPack.addResource(ResourceType.CLIENT_RESOURCES, soundsJsonid, jsonBytes)
        RRPCallback.BEFORE_VANILLA.register { it += genericPack }
    }

    fun processSounds(): Map<String, SoundEntry> {
        return Registry.ITEM.stream().flatMap { item ->
            val id = Registry.ITEM.getId(item)
            val mapper = mappers[id.namespace]
            try {
                val def: ItemSound = mapper?.generator?.invoke(item)
                    ?: if (item is BlockItem) ItemSound(Sounds.aliased(item.block.getSoundGroup(item.block.defaultState).placeSound))
                    else ItemSound(Sounds.aliased(Sounds.ITEM_PICK))

                val pickup = Pair(SoundEvent(Identifier(getClickId(id, SoundType.PICKUP))), def.pickup)
                val entries = mutableListOf<Pair<SoundEvent, SoundEntry>>()
                entries += pickup

                entries.addAll(
                    mapOf(SoundType.PLACE to def.place, SoundType.HOTBAR to def.hotbar).map {
                        val clickId = Identifier(getClickId(id, it.key))
                        if (it.value != pickup.second) Pair(SoundEvent(clickId), it.value)
                        else Pair(SoundEvent(clickId), Sounds.aliased(pickup.first))
                    })
                return@flatMap entries.stream()
            } catch (e: Exception) {
                System.err.println("Failed to generate sounds for ${mapper?.namespace} provided by ${mapper?.modId}")
                e.printStackTrace()
                return@flatMap listOf<Pair<SoundEvent, SoundEntry>>().stream()
            }
        }.collect(Collectors.toMap({ it.first.id.path }, Pair<SoundEvent, SoundEntry>::second))
    }

    fun getCache(): String? {
        if (Files.exists(cachePath) && !DebugUtils.skipCache)
            try {
                val lines = Files.readAllLines(cachePath)
                if (CacheInfo.fromString(lines[0]) == CacheInfo.current) {
                    return lines[1]
                } else {
                    DebugUtils.genericLog("Invalidating ExtraSounds cache.")
                    DebugUtils.genericLog("Previous: ${lines[0]}")
                    DebugUtils.genericLog("New: ${CacheInfo.current}")
                }
            } catch (e: Exception) {
                System.err.println("Failed to load ExtraSounds cache.")
                e.printStackTrace()
            }
        else DebugUtils.genericLog("Cache not found - generating...")
        return null
    }

    fun writeCache(cache: String) {
        try {
            Files.write(
                cachePath,
                "${CacheInfo.current}\n$cache".toByteArray(),
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE
            )
        } catch (e: IOException) {
            System.err.println("Failed to save ExtraSounds cache.")
            e.printStackTrace()
        }
    }

    data class CacheInfo(val version: Int, val itemCount: Int, val mappers: List<String>) {
        companion object {
            val current by lazy {
                CacheInfo(
                    cacheVersion,
                    Registry.ITEM.size(),
                    mappers.values.map { getModVersion(it.modId) })
            }

            fun fromString(s: String): CacheInfo {
                return try {
                    s.split(";").let {
                        CacheInfo(Integer.parseInt(it[0]), Integer.parseInt(it[1]), it[2].split(","))
                    }
                } catch (e: Exception) {
                    CacheInfo(0, 0, listOf())
                }
            }

            private fun getModVersion(modId: String): String {
                return FabricLoader.getInstance()
                    .getModContainer(modId)
                    .map { modContainer ->
                        "$modId ${modContainer.metadata.version.friendlyString}"
                    }
                    .orElse("nope")
            }
        }

        override fun toString(): String {
            return "%d;%d;%s".format(version, itemCount, mappers.joinToString(","))
        }
    }
}