package dev.stashy.extrasounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Sounds
{
    public static final Identifier CHAT_ID = new Identifier("extrasounds:chat.message");
    public static SoundEvent CHAT = new SoundEvent(CHAT_ID);

    public static final Identifier CHAT_MENTION_ID = new Identifier("extrasounds:chat.mention");
    public static SoundEvent CHAT_MENTION = new SoundEvent(CHAT_MENTION_ID);

    public static final Identifier HOTBAR_SCROLL_ID = new Identifier("extrasounds:hotbar_scroll");
    public static SoundEvent HOTBAR_SCROLL = new SoundEvent(HOTBAR_SCROLL_ID);

    public static final Identifier INVENTORY_OPEN_ID = new Identifier("extrasounds:inventory.open");
    public static SoundEvent INVENTORY_OPEN = new SoundEvent(INVENTORY_OPEN_ID);

    public static final Identifier INVENTORY_CLOSE_ID = new Identifier("extrasounds:inventory.close");
    public static SoundEvent INVENTORY_CLOSE = new SoundEvent(INVENTORY_CLOSE_ID);

    public static final Identifier INVENTORY_SCROLL_ID = new Identifier("extrasounds:inventory.scroll");
    public static SoundEvent INVENTORY_SCROLL = new SoundEvent(INVENTORY_SCROLL_ID);

    public static final Identifier ITEM_DROP_ID = new Identifier("extrasounds:item.drop");
    public static SoundEvent ITEM_DROP = new SoundEvent(ITEM_DROP_ID);

    public static final Identifier ITEM_PICK_ID = new Identifier("extrasounds:item.pickup");
    public static SoundEvent ITEM_PICK = new SoundEvent(ITEM_PICK_ID);

    public static final Identifier ITEM_PICK_ALL_ID = new Identifier("extrasounds:item.pickup_all");
    public static SoundEvent ITEM_PICK_ALL = new SoundEvent(ITEM_PICK_ALL_ID);

    public static final Identifier ITEM_CLONE_ID = new Identifier("extrasounds:item.clone");
    public static SoundEvent ITEM_CLONE = new SoundEvent(ITEM_CLONE_ID);

    public static final Identifier ITEM_DELETE_ID = new Identifier("extrasounds:item.delete");
    public static SoundEvent ITEM_DELETE = new SoundEvent(ITEM_DELETE_ID);

    public static final Identifier ITEM_DRAG_ID = new Identifier("extrasounds:item.drag");
    public static SoundEvent ITEM_DRAG = new SoundEvent(ITEM_DRAG_ID);

    public static final Identifier EFFECT_ADD_POSITIVE_ID = new Identifier("extrasounds:effect.add.positive");
    public static SoundEvent EFFECT_ADD_POSITIVE = new SoundEvent(EFFECT_ADD_POSITIVE_ID);

    public static final Identifier EFFECT_ADD_NEGATIVE_ID = new Identifier("extrasounds:effect.add.negative");
    public static SoundEvent EFFECT_ADD_NEGATIVE = new SoundEvent(EFFECT_ADD_NEGATIVE_ID);

    public static final Identifier EFFECT_REMOVE_POSITIVE_ID = new Identifier("extrasounds:effect.remove.positive");
    public static SoundEvent EFFECT_REMOVE_POSITIVE = new SoundEvent(EFFECT_REMOVE_POSITIVE_ID);

    public static final Identifier EFFECT_REMOVE_NEGATIVE_ID = new Identifier("extrasounds:effect.remove.negative");
    public static SoundEvent EFFECT_REMOVE_NEGATIVE = new SoundEvent(EFFECT_REMOVE_NEGATIVE_ID);

    protected static void registerAll()
    {
        register(CHAT_ID, CHAT);
        register(CHAT_MENTION_ID, CHAT_MENTION);
        register(HOTBAR_SCROLL_ID, HOTBAR_SCROLL);
        register(INVENTORY_OPEN_ID, INVENTORY_OPEN);
        register(INVENTORY_CLOSE_ID, INVENTORY_CLOSE);
        register(INVENTORY_SCROLL_ID, INVENTORY_SCROLL);
        register(ITEM_DROP_ID, ITEM_DROP);
        register(ITEM_PICK_ID, ITEM_PICK);
        register(ITEM_PICK_ALL_ID, ITEM_PICK_ALL);
        register(ITEM_CLONE_ID, ITEM_CLONE);
        register(ITEM_DELETE_ID, ITEM_DELETE);
        register(ITEM_DRAG_ID, ITEM_DRAG);
    }

    private static void register(Identifier id, SoundEvent e)
    {
        Registry.register(Registry.SOUND_EVENT, id, e);
    }
}
