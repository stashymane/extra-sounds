package dev.stashy.extrasounds.sounds;

import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.List;

public class Sounds extends SoundRegistry
{
    public static final SoundEvent CHAT = register("chat.message");
    public static final SoundEvent CHAT_MENTION = register("chat.mention");
    public static final SoundEvent HOTBAR_SCROLL = register("hotbar_scroll");
    public static final SoundEvent INVENTORY_OPEN = register("inventory.open");
    public static final SoundEvent INVENTORY_CLOSE = register("inventory.close");
    public static final SoundEvent INVENTORY_SCROLL = register("inventory.scroll");
    public static final SoundEvent ITEM_DROP = register("item.drop");
    public static final SoundEvent ITEM_PICK = register("item.pickup");
    public static final SoundEvent ITEM_PICK_ALL = register("item.pickup_all");
    public static final SoundEvent ITEM_CLONE = register("item.clone");
    public static final SoundEvent ITEM_DELETE = register("item.delete");
    public static final SoundEvent ITEM_DRAG = register("item.drag");
    public static final SoundEvent EFFECT_ADD_POSITIVE = register("effect.add.positive");
    public static final SoundEvent EFFECT_ADD_NEGATIVE = register("effect.add.negative");
    public static final SoundEvent EFFECT_REMOVE_POSITIVE = register("effect.remove.positive");
    public static final SoundEvent EFFECT_REMOVE_NEGATIVE = register("effect.remove.negative");
    public static final SoundEvent KEYBOARD_TYPE = register("keyboard.type");

    public class Actions
    {
        public static final SoundEvent BOW_PULL = register("item.action.bow");
    }

    public static SoundEntry aliased(SoundEvent e)
    {
        return single(e.getId(), 1f, 1f, Sound.RegistrationType.SOUND_EVENT);
    }

    public static SoundEntry event(Identifier id)
    {
        return single(id, 1f, 1.7f, Sound.RegistrationType.SOUND_EVENT);
    }

    public static SoundEntry single(Identifier id, float volume, float pitch, Sound.RegistrationType type)
    {
        return new SoundEntry(List.of(
                new Sound(id.toString(), volume, pitch, 1, type, false, false, 16)
        ), false, null);
    }
}
