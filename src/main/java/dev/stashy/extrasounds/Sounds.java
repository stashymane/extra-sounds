package dev.stashy.extrasounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class Sounds
{
    public static final Identifier CHAT_ID = new Identifier("extrasounds:chat");
    public static SoundEvent CHAT = new SoundEvent(CHAT_ID);

    public static final Identifier CHAT_MENTION_ID = new Identifier("extrasounds:chat_mention");
    public static SoundEvent CHAT_MENTION = new SoundEvent(CHAT_MENTION_ID);

    public static final Identifier HOTBAR_SCROLL_ID = new Identifier("extrasounds:hotbar_scroll");
    public static SoundEvent HOTBAR_SCROLL = new SoundEvent(HOTBAR_SCROLL_ID);

    public static final Identifier INVENTORY_OPEN_ID = new Identifier("extrasounds:inventory_open");
    public static SoundEvent INVENTORY_OPEN = new SoundEvent(INVENTORY_OPEN_ID);

    public static final Identifier INVENTORY_CLOSE_ID = new Identifier("extrasounds:inventory_close");
    public static SoundEvent INVENTORY_CLOSE = new SoundEvent(INVENTORY_CLOSE_ID);

    public static final Identifier INVENTORY_SCROLL_ID = new Identifier("extrasounds:inventory_scroll");
    public static SoundEvent INVENTORY_SCROLL = new SoundEvent(INVENTORY_SCROLL_ID);

    public static final Identifier ITEM_DROP_ID = new Identifier("extrasounds:item_drop");
    public static SoundEvent ITEM_DROP = new SoundEvent(ITEM_DROP_ID);

    public static final Identifier ITEM_PICK_ID = new Identifier("extrasounds:item_pickup");
    public static SoundEvent ITEM_PICK = new SoundEvent(ITEM_PICK_ID);

    public static final Identifier ITEM_PICK_ALL_ID = new Identifier("extrasounds:item_pickup_all");
    public static SoundEvent ITEM_PICK_ALL = new SoundEvent(ITEM_PICK_ALL_ID);

    public static final Identifier ITEM_CLONE_ID = new Identifier("extrasounds:item_clone");
    public static SoundEvent ITEM_CLONE = new SoundEvent(ITEM_CLONE_ID);

    public static final Identifier ITEM_DELETE_ID = new Identifier("extrasounds:item_delete");
    public static SoundEvent ITEM_DELETE = new SoundEvent(ITEM_DELETE_ID);

    public static final Identifier ITEM_DRAG_ID = new Identifier("extrasounds:item_drag");
    public static SoundEvent ITEM_DRAG = new SoundEvent(ITEM_DRAG_ID);

}
