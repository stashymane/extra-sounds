package dev.stashy.extrasounds;

import dev.stashy.soundcategories.SoundCategories;
import net.minecraft.sound.SoundCategory;

public class Mixers
{
    public static SoundCategory INVENTORY;
    public static SoundCategory CHAT;
    public static SoundCategory EFFECTS;

    public static void init()
    {
        SoundCategories.register("INVENTORY", (it) -> {Mixers.INVENTORY = it;});
        SoundCategories.register("CHAT", (it) -> {Mixers.CHAT = it;});
        SoundCategories.register("EFFECTS", (it) -> {Mixers.EFFECTS = it;});
    }
}
