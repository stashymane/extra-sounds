package dev.stashy.extrasounds;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collections;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Logger;

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
                                          .setSelections(Registry.SOUND_EVENT)
                                          .setDefaultValue(() -> Utils.getUnsafely(field, defaults))
                                          .setSaveConsumer(newValue -> Utils.setUnsafely(field, config, newValue))
                                          .build()
                ), SoundEvent.class);
    }

    public static void inventoryClick(Slot slot, ItemStack cursor, SlotActionType actionType)
    {
        ItemStack clicked = slot.getStack();
        boolean hasCursor = !cursor.isEmpty();
        boolean hasSlot = !clicked.isEmpty();

        switch (actionType)
        {
            case PICKUP_ALL:
                if (hasCursor)
                    ExtraSounds.playSound(ExtraSounds.config.itemPickupAll);
                return;
            case CLONE:
                ExtraSounds.playSound(ExtraSounds.config.itemClone);
                return;
            case QUICK_MOVE:
                if (MinecraftClient.getInstance().player != null &&
                        !(MinecraftClient.getInstance().player.currentScreenHandler instanceof PlayerScreenHandler)
                        && MinecraftClient.getInstance().player.currentScreenHandler
                        .slots.parallelStream()
                              .filter((s) -> s.inventory != slot.inventory)
                              .filter((s) -> !(s.inventory instanceof CraftingInventory || s.inventory instanceof CraftingResultInventory))
                              .noneMatch(
                                      (s) -> !s.hasStack() || s.getStack().getItem()
                                                               .equals(slot.getStack().getItem()) && s
                                              .getStack().getCount() < s.getStack().getMaxCount()))
                    return;
            default:
                if (hasCursor)
                    ExtraSounds.playItemSound(cursor, false);
                else if (hasSlot)
                    ExtraSounds.playItemSound(clicked, true);
        }
    }

    private static long lastPlayed = System.currentTimeMillis();

    public static void playItemSound(ItemStack stack, boolean pickup)
    {
        long now = System.currentTimeMillis();
        if (now - lastPlayed > 5)
        {
            InventorySound is = ((ItemSoundContainer) stack.getItem()).getInventorySound();
            if (is == null)
            {
                Logger.getGlobal().warning(stack.getItem() + " does not have an inventory sound assigned to it.");
                return;
            }
            playSound(is.sound, is.baseVol * config.itemPickup.volume,
                      getItemPitch(config.itemPickup.pitch, config.itemPickup.pitchRange, pickup));
            lastPlayed = now;
        }
    }

    public static void playSound(SoundConfig.SoundSource src)
    {
        playSound(src, getRandomPitch(src.pitch, src.pitchRange));
    }

    public static void playSound(SoundConfig.SoundSource src, float pitch)
    {
        playSound(src.sound, src.volume, pitch);
    }

    private static void playSound(SoundEvent snd, float vol, float pitch)
    {
        if (MinecraftClient.getInstance().player != null)
            MinecraftClient.getInstance().execute(
                    () -> MinecraftClient.getInstance().player.playSound(snd, vol * config.masterVolume, pitch));
    }

    public static float getRandomPitch(float pitch, float pitchRange)
    {
        return pitch - pitchRange / 2 + r.nextFloat() * pitchRange;
    }

    public static float getItemPitch(float pitch, float pitchRange, boolean pickup)
    {
        if (pickup)
            return pitch + pitchRange / 2;
        else
            return pitch - pitchRange / 2;
    }
}