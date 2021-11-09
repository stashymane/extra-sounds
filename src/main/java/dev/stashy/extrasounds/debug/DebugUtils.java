package dev.stashy.extrasounds.debug;

import dev.stashy.extrasounds.mapping.SoundPackLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.SoundEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

public class DebugUtils
{
    public static final String debugVar = "extrasounds.debug";
    public static final String debugPathVar = "extrasounds.debug.path";

    public static final boolean debug = System.getProperties().containsKey(debugVar)
            && System.getProperty(debugVar).equals("true");
    public static final String debugPath = System.getProperties().containsKey(debugPathVar)
            ? System.getProperty(debugPathVar) : "debug/";

    private static final Logger LOGGER = LogManager.getLogger();

    public static void init()
    {
        if (!debug) return;
        LOGGER.info("ExtraSounds: DEBUG mode enabled.");
        LOGGER.info("Debug path: " + Path.of(debugPath).toAbsolutePath());
    }

    public static void exportSoundsJson(byte[] jsonData)
    {
        if (!debug) return;
        try
        {
            Path p = Path.of(debugPath).resolve("sounds.json");
            createFile(p);
            Files.write(p, jsonData, StandardOpenOption.TRUNCATE_EXISTING);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void exportGenerators()
    {
        if (!debug) return;
        Path p = Path.of(debugPath).resolve("generators.txt");
        createFile(p);
        try
        {
            Files.write(p, SoundPackLoader.mappers.keySet().stream()
                                                  .map(it -> {
                                                      var clazz = SoundPackLoader.mappers.get(it).itemSoundGenerator()
                                                                                         .getClass();
                                                      return "namespace: " + it + "; class: " + (clazz == null ? "none" : clazz.getName());
                                                  })
                                                  .collect(
                                                          Collectors.toList()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void soundLog(SoundEvent snd)
    {
        if (!debug) return;
        if (snd.getId().getPath().startsWith("item.click"))
            LOGGER.info("Playing sound: " + snd.getId());
    }

    public static void effectLog(StatusEffect effect, boolean add)
    {
        if (!debug) return;
        boolean positive = !effect.getCategory().equals(StatusEffectCategory.HARMFUL);
        LOGGER.info(
                (positive ? "Positive" : "Negative") + " effect " + (add ? "added" : "removed") + ": " + effect.getName()
                                                                                                               .getString());
    }

    private static void createFile(Path p)
    {
        try
        {
            if (!Files.isDirectory(Path.of(debugPath)))
                Files.createDirectory(Path.of(debugPath));
            if (!Files.exists(p))
                Files.createFile(p);
        }
        catch (IOException e)
        {
            LOGGER.error("Unable to create file: " + p);
            e.printStackTrace();
        }
    }
}
