package dev.stashy.extrasounds.mapping;

import dev.stashy.extrasounds.ExtraSounds;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public record SoundMapper(String namespace,
                          Function<Identifier, SoundEntry> itemSoundMapper)
{
    public static SoundMapper of(String modId, Function<Identifier, SoundEntry> itemSoundMapper)
    {
        return new SoundMapper(modId, itemSoundMapper);
    }

    public static void register(String modId, Function<Identifier, SoundEntry> itemSoundMapper)
    {
        FabricLoader.getInstance().getEntrypoints(ExtraSounds.MODID, SoundMapper.class)
                    .add(SoundMapper.of(modId, itemSoundMapper));
    }
}
