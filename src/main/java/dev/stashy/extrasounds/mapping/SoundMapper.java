package dev.stashy.extrasounds.mapping;

import dev.stashy.extrasounds.ExtraSounds;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public record SoundMapper(String namespace,
                          Function<Identifier, SoundEntry> itemSoundMapper)
{
    public static SoundMapper of(String namespace, Function<Identifier, SoundEntry> itemSoundMapper)
    {
        return new SoundMapper(namespace, itemSoundMapper);
    }

    public static void register(String namespace, Function<Identifier, SoundEntry> itemSoundMapper)
    {
        FabricLoader.getInstance().getEntrypoints(ExtraSounds.MODID, SoundMapper.class)
                    .add(SoundMapper.of(namespace, itemSoundMapper));
    }
}
