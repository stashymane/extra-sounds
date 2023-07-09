package dev.stashy.extrasounds;

import dev.stashy.soundcategories.CategoryLoader;
import net.minecraft.sound.SoundCategory;

public class Mixers implements CategoryLoader
{
    @CategoryLoader.Register(id = "EXTRASOUNDS_MASTER", master = true, defaultLevel = 0.5f)
    public static SoundCategory Master = SoundCategory.MASTER;

    @CategoryLoader.Register
    public static SoundCategory Inventory = SoundCategory.MASTER;

    @CategoryLoader.Register
    public static SoundCategory Action = SoundCategory.MASTER;

    @CategoryLoader.Register
    public static SoundCategory Chat = SoundCategory.MASTER;

    @CategoryLoader.Register
    public static SoundCategory ChatMention = SoundCategory.MASTER;

    @CategoryLoader.Register
    public static SoundCategory Effects = SoundCategory.MASTER;

    @CategoryLoader.Register
    public static SoundCategory Scroll = SoundCategory.MASTER;

    @CategoryLoader.Register(defaultLevel = 0f)
    public static SoundCategory Typing = SoundCategory.MASTER;
}
