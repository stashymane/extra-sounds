package dev.stashy.extrasounds;

import dev.stashy.extrasounds.debug.DebugUtils;
import dev.stashy.extrasounds.mapping.SoundPackLoader;
import dev.stashy.extrasounds.sounds.SoundType;
import dev.stashy.extrasounds.sounds.Sounds;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;

import java.util.Random;

public class ExtraSounds implements ClientModInitializer
{
    public static final String MODID = "extrasounds";
    private static final Random r = new Random();

    @Override
    public void onInitializeClient()
    {
        SoundPackLoader.init();
        DebugUtils.init();
    }

    public static void hotbar(int i)
    {
        ItemStack stack = MinecraftClient.getInstance().player.getInventory().getStack(i);
        if (stack.getItem() == Items.AIR)
            SoundManager.playSound(Sounds.HOTBAR_SCROLL, SoundType.HOTBAR);
        else
            SoundManager.playSound(stack, SoundType.HOTBAR);
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
                    SoundManager.playSound(Sounds.ITEM_PICK_ALL, SoundType.PICKUP);
                return;
            case CLONE:
                SoundManager.playSound(Sounds.ITEM_CLONE, SoundType.PLACE);
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
                if (hasCursor) SoundManager.playSound(cursor, SoundType.PLACE);
                else if (hasSlot)
                    SoundManager.playSound(clicked, SoundType.PICKUP);
        }
    }

    public static String getClickId(Identifier id)
    {
        return getClickId(id, true);
    }

    public static String getClickId(Identifier id, boolean includeNamespace)
    {
        return (includeNamespace ? "extrasounds:" : "") + "item.click." + id.getNamespace() + "." + id.getPath();
    }
}