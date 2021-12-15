package dev.stashy.extrasounds;

import dev.stashy.extrasounds.debug.DebugUtils;
import dev.stashy.extrasounds.mapping.SoundPackLoader;
import dev.stashy.extrasounds.sounds.Sounds;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class ExtraSounds implements ClientModInitializer
{
    public static final String MODID = "extrasounds";
    private static final Random r = new Random();
    private static long lastPlayed = System.currentTimeMillis();
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitializeClient()
    {
        Mixers.init();
        SoundPackLoader.init();
        DebugUtils.init();
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
                    ExtraSounds.playSound(Sounds.ITEM_PICK_ALL, Mixers.INVENTORY);
                return;
            case CLONE:
                ExtraSounds.playSound(Sounds.ITEM_CLONE, Mixers.INVENTORY);
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

    public static String getClickId(Identifier id, boolean includeNamespace)
    {
        return (includeNamespace ? "extrasounds:" : "") + "item.click." + id.getNamespace() + "." + id.getPath();
    }

    public static String getClickId(Identifier id)
    {
        return getClickId(id, true);
    }

    public static void playItemSound(ItemStack stack, boolean pickup)
    {
        var itemId = Registry.ITEM.getId(stack.getItem());
        String idString = getClickId(itemId);
        if (!Identifier.isValid(idString))
        {
            LOGGER.error("Unable to parse sound from ID: " + idString);
            return;
        }

        Identifier id = Identifier.tryParse(idString);
        Registry.SOUND_EVENT.getOrEmpty(id).ifPresentOrElse(
                (snd) -> playSound(snd, Mixers.INVENTORY, getItemPitch(1f, 0.1f, pickup)),
                () -> LOGGER.error("Sound cannot be found in registry: " + id));
    }

    public static void playEffectSound(StatusEffect effect, boolean add)
    {
        DebugUtils.effectLog(effect, add);
        playSound(
                add ?
                        switch (effect.getCategory())
                                {
                                    case HARMFUL -> Sounds.EFFECT_ADD_NEGATIVE;
                                    case NEUTRAL, BENEFICIAL -> Sounds.EFFECT_ADD_POSITIVE;
                                }
                        :
                        switch (effect.getCategory())
                                {
                                    case HARMFUL -> Sounds.EFFECT_REMOVE_NEGATIVE;
                                    case NEUTRAL, BENEFICIAL -> Sounds.EFFECT_REMOVE_POSITIVE;
                                },
                Mixers.EFFECTS);
    }

    public static void playSound(SoundEvent snd)
    {
        playSound(snd, Mixers.INVENTORY);
    }

    public static void playSound(SoundEvent snd, SoundCategory cat)
    {
        playSound(snd, cat, 1f);
    }

    public static void playSound(SoundEvent snd, SoundCategory cat, float pitch)
    {
        long now = System.currentTimeMillis();
        if (now - lastPlayed > 5)
        {
            MinecraftClient.getInstance().getSoundManager()
                           .play(new PositionedSoundInstance(snd.getId(), cat, 1f, pitch, false, 0,
                                                             SoundInstance.AttenuationType.NONE, 0.0D, 0.0D, 0.0D,
                                                             true));
            lastPlayed = now;
            DebugUtils.soundLog(snd);
        }
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