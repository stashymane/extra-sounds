package dev.stashy.extrasounds;

import dev.stashy.soundcategories.CategoryLoader;
import net.minecraft.sound.SoundCategory;

public class Mixers implements CategoryLoader
{
    @Register(id = "EXTRASOUNDS_MASTER", master = true)
    public static SoundCategory MASTER;
    @Register
    public static SoundCategory INVENTORY;
    @Register
    public static SoundCategory CHAT;
    @Register
    public static SoundCategory EFFECTS;
    @Register
    public static SoundCategory SCROLL;
    @Register(defaultLevel = 0f)
    public static SoundCategory TYPING;
}
