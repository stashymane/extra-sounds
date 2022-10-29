package dev.stashy.extrasounds

import dev.stashy.extrasounds.mapping.SoundLoader
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.sound.SoundEvent
import org.apache.logging.log4j.LogManager
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption


object DebugUtils {
    const val debugVar = "extrasounds.debug"
    const val debugPathVar = "extrasounds.debug.path"
    const val noCacheVar = "extrasounds.nocache"

    val debug = System.getProperties()[debugVar]?.equals("true") ?: false
    val debugPath = System.getProperties()[debugPathVar]?.toString() ?: "debug/"
    val skipCache = System.getProperties()[noCacheVar]?.equals("true") ?: false

    val logger = LogManager.getLogger()

    fun init() {
        if (!debug) return
        logger.info("ExtraSounds DEBUG mode enabled.")
        logger.info("Debug path: ${Path.of(debugPath).toAbsolutePath()}")
    }

    fun genericLog(msg: String) {
        if (!debug) return
        logger.info(msg)
    }

    fun soundLog(snd: SoundEvent) {
        if (!debug) return
        logger.info("Playing sound: ${snd.id}")
    }

    fun effectLog(effect: StatusEffect, add: Boolean) {
        if (!debug) return
        val positive = !effect.category.equals(StatusEffectCategory.HARMFUL)
        logger.info("${if (positive) "Positive" else "Negative"} effect ${if (add) "added" else "removed"}: ${effect.name.string}")
    }

    fun exportSoundsJson(data: ByteArray) {
        if (!debug) return
        try {
            val p = Path.of(debugPath).resolve("sounds.json")
            createFile(p)
            Files.write(p, data, StandardOpenOption.TRUNCATE_EXISTING)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun exportGenerators() {
        if (!debug) return
        val p = Path.of(debugPath).resolve("generators.txt")
        createFile(p)
        try {
            Files.write(p, SoundLoader.mappers.keys.stream()
                .map { it ->
                    val clazz = SoundLoader.mappers[it]?.javaClass
                    "namespace: " + it + "; class: " + if (clazz == null) "none" else clazz.name
                }
                .toList())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun createFile(p: Path) {
        try {
            if (!Files.isDirectory(Path.of(debugPath))) Files.createDirectory(Path.of(debugPath))
            if (!Files.exists(p)) Files.createFile(p)
        } catch (e: IOException) {
            logger.error("Unable to create file: $p")
            e.printStackTrace()
        }
    }
}