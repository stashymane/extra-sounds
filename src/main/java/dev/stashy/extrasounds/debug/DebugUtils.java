package dev.stashy.extrasounds.debug;

import net.minecraft.sound.SoundEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class DebugUtils
{
    public static final String debugVar = "extrasounds.debug";
    public static final String debugPathVar = "extrasounds.debug.path";

    public static final boolean debug = System.getenv().containsKey(debugVar)
            && System.getenv(debugVar).equals("true");
    public static final String debugPath = System.getenv().containsKey(debugPathVar)
            ? System.getenv(debugPathVar) : "C:/";

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
            Path p = Path.of("D:\\test.json");
            if (!Files.exists(p))
                Files.createFile(p);
            Files.write(Path.of(debugPath).resolve("sounds.json"), jsonData,
                        StandardOpenOption.TRUNCATE_EXISTING);
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
}
