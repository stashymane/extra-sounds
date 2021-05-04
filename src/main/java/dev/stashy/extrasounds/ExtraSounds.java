package dev.stashy.extrasounds;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

public class ExtraSounds implements ModInitializer
{
    public static SoundConfig config;
    private static final Function<SoundEvent, Text> registryTextProvider = T -> new TranslatableText(
            T.getId().getPath());

    private static final Random r = new Random();

    @Override
    public void onInitialize()
    {
        AutoConfig.register(SoundConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(SoundConfig.class).getConfig();
        GuiRegistry registry = AutoConfig.getGuiRegistry(SoundConfig.class);
        registry.registerTypeProvider(
                (i13n, field, config, defaults, guiProvider) -> Collections.singletonList(
                        ConfigEntryBuilder.create().startDropdownMenu(
                                new TranslatableText(i13n),
                                DropdownMenuBuilder.TopCellElementBuilder.of(
                                        Utils.getUnsafely(field, config, Utils.getUnsafely(field, defaults)),
                                        str -> Registry.SOUND_EVENT.get(Identifier.tryParse(str)),
                                        registryTextProvider
                                ),
                                DropdownMenuBuilder.CellCreatorBuilder.ofWidth(200, registryTextProvider)
                        )
                                          .setTooltipSupplier(soundEvent -> Optional
                                                  .of(new TranslatableText[]{new TranslatableText(
                                                          "subtitles." + soundEvent.getId().getPath())}))
                                          .setSelections(Registry.SOUND_EVENT)
                                          .setDefaultValue(() -> Utils.getUnsafely(field, defaults))
                                          .setSaveConsumer(newValue -> Utils.setUnsafely(field, config, newValue))
                                          .build()
                ), SoundEvent.class);
    }

    public static void playSound(SoundConfig.SoundSource src)
    {
        playSound(src, src.pitch - src.pitchRange / 2 + r.nextFloat() * src.pitchRange);
    }

    public static void playSound(SoundConfig.SoundSource src, float pitch)
    {
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player
                .playSound(src.sound, src.volume * config.masterVolume,
                           Math.min(2, pitch));
    }
}