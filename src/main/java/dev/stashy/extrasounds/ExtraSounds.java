package dev.stashy.extrasounds;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

import java.util.Random;

public class ExtraSounds implements ClientModInitializer
{
    private static final Random r = new Random();
    private static long lastPlayed = System.currentTimeMillis();

    @Override
    public void onInitializeClient()
    {
        Sounds.registerAll();
        SoundPack.init();
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
                    ExtraSounds.playSound(Sounds.ITEM_PICK_ALL);
                return;
            case CLONE:
                ExtraSounds.playSound(Sounds.ITEM_CLONE);
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

    public static void playItemSound(ItemStack stack, boolean pickup)
    {
        SoundEvent e = Sounds.ITEM_PICK;
        playSound(e,
                  getItemPitch(1f, 0.1f, pickup));
    }

    public static void playSound(SoundEvent snd)
    {
        playSound(snd, 1f);
    }

    public static void playSound(SoundEvent snd, float pitch)
    {
        if (MinecraftClient.getInstance().player == null)
            return;
        long now = System.currentTimeMillis();
        if (now - lastPlayed > 5)
        {
            MinecraftClient.getInstance().execute(
                    () -> MinecraftClient.getInstance().player.playSound(snd, SoundCategory.BLOCKS, 1f, pitch));
            lastPlayed = now;
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