package dev.stashy.extrasounds.mapping;

import net.minecraft.client.sound.SoundEntry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public record SoundGenerator(String namespace,
                             Function<Identifier, SoundEntry> itemSoundGenerator)
{
    public static SoundGenerator of(String namespace, Function<Identifier, SoundEntry> itemSoundGenerator)
    {
        return new SoundGenerator(namespace, itemSoundGenerator);
    }
}
