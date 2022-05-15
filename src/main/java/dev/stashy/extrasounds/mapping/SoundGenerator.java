package dev.stashy.extrasounds.mapping;

import net.minecraft.item.Item;

import java.util.function.Function;

public record SoundGenerator(String namespace,
                             Function<Item, SoundDefinition> itemSoundGenerator)
{
    public static SoundGenerator of(String namespace, Function<Item, SoundDefinition> itemSoundGenerator)
    {
        return new SoundGenerator(namespace, itemSoundGenerator);
    }
}
