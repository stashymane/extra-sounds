package dev.stashy.extrasounds.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Sounds
{
    public static SoundEvent CHAT = register("chat.message");
    public static SoundEvent CHAT_MENTION = register("chat.mention");
    public static SoundEvent HOTBAR_SCROLL = register("hotbar_scroll");
    public static SoundEvent INVENTORY_OPEN = register("inventory.open");
    public static SoundEvent INVENTORY_CLOSE = register("inventory.close");
    public static SoundEvent INVENTORY_SCROLL = register("inventory.scroll");
    public static SoundEvent ITEM_DROP = register("item.drop");
    public static SoundEvent ITEM_PICK = register("item.pickup");
    public static SoundEvent ITEM_PICK_ALL = register("item.pickup_all");
    public static SoundEvent ITEM_CLONE = register("item.clone");
    public static SoundEvent ITEM_DELETE = register("item.delete");
    public static SoundEvent ITEM_DRAG = register("item.drag");
    public static SoundEvent EFFECT_ADD_POSITIVE = register("effect.add.positive");
    public static SoundEvent EFFECT_ADD_NEGATIVE = register("effect.add.negative");
    public static SoundEvent EFFECT_REMOVE_POSITIVE = register("effect.remove.positive");
    public static SoundEvent EFFECT_REMOVE_NEGATIVE = register("effect.remove.negative");
    public static SoundEvent KEYBOARD_TYPE = register("keyboard.type");

    private static SoundEvent register(Identifier id)
    {
        var e = new SoundEvent(id);
        Registry.register(Registry.SOUND_EVENT, id, e);
        return e;
    }

    private static SoundEvent register(String id)
    {
        return register(new Identifier("extrasounds:" + id));
    }
}
