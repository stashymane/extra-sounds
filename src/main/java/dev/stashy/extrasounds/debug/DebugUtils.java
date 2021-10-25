package dev.stashy.extrasounds.debug;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.SoundEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DebugUtils
{
    public static final String debugVar = "extrasounds.debug";
    public static final String debugPathVar = "extrasounds.debug.path";

    public static final boolean debug = System.getProperties().containsKey(debugVar)
            && System.getProperty(debugVar).equals("true");
    public static final String debugPath = System.getProperties().containsKey(debugPathVar)
            ? System.getProperty(debugPathVar) : "C:\\";

    public static void init()
    {
        if (!debug) return;
        System.out.println("ExtraSounds: DEBUG mode enabled.");
        System.out.println("Debug path: " + debugPath);
    }

    public static void exportSoundsJson(byte[] jsonData)
    {
        if (!debug) return;
        try
        {
            Path p = Path.of(debugPath).resolve("sounds.json");
            if (!Files.exists(p))
                Files.createFile(p);
            Files.write(p, jsonData, StandardOpenOption.TRUNCATE_EXISTING);
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
            System.out.println("Playing sound: " + snd.getId());
    }

    public static void effectLog(StatusEffect effect, boolean add)
    {
        boolean positive = !effect.getCategory().equals(StatusEffectCategory.HARMFUL);
        if (!debug) return;
        System.out.println((positive ? "Positive" : "Negative") + " effect " + (add ? "added" : "removed"));
    }
}
