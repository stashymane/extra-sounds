package dev.stashy.extrasounds;

import dev.stashy.soundcategories.CategoryLoader;
import net.minecraft.sound.SoundCategory;

public class Mixers implements CategoryLoader
{
    @CategoryLoader.Register
    public static SoundCategory INTERFACE;
    @CategoryLoader.Register
    public static SoundCategory CHAT;
    @CategoryLoader.Register
    public static SoundCategory EFFECTS;
}
