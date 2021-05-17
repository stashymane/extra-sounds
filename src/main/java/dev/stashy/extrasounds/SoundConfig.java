package dev.stashy.extrasounds;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

@Config(name = "extrasounds")
public class SoundConfig implements ConfigData
{
    public float masterVolume = 1f;
    public boolean blockBasedSound = true;

    @ConfigEntry.Gui.CollapsibleObject
    public SoundSource chat = new SoundSource(SoundEvents.BLOCK_NOTE_BLOCK_HAT, 0.2f, 2f, 0.1f);
    @ConfigEntry.Gui.CollapsibleObject
    public SoundSource chatMention = new SoundSource(SoundEvents.BLOCK_NOTE_BLOCK_BELL, 0.5f, 1.2f, 0.1f);

    @ConfigEntry.Gui.CollapsibleObject
    public SoundSource hotbarScroll = new SoundSource(SoundEvents.BLOCK_NOTE_BLOCK_HAT, 0.1f, 2f, 0.2f);
    @ConfigEntry.Gui.CollapsibleObject
    public SoundSource inventoryOpen = new SoundSource(SoundEvents.UI_TOAST_IN, 0.2f, 1.5f, 0.1f);
    @ConfigEntry.Gui.CollapsibleObject
    public SoundSource inventoryClose = new SoundSource(SoundEvents.UI_TOAST_OUT, 0.2f, 1.5f, 0.1f);
    @ConfigEntry.Gui.CollapsibleObject
    public SoundSource listScroll = new SoundSource(SoundEvents.BLOCK_NOTE_BLOCK_HAT, 0.1f, 1f, 0.3f);

    @ConfigEntry.Gui.CollapsibleObject
    public SoundSource itemDrop = new SoundSource(SoundEvents.BLOCK_DISPENSER_LAUNCH, 0.1f, 1.5f, 0.1f);

    @ConfigEntry.Gui.CollapsibleObject
    public SoundSource itemPickup = new SoundSource(SoundEvents.BLOCK_GLASS_STEP, 0.2f, 1.5f, 0.1f);
    @ConfigEntry.Gui.CollapsibleObject
    public SoundSource itemPickupAll = new SoundSource(SoundEvents.ENTITY_ITEM_PICKUP, 0.02f, 2f, 0.1f);
    @ConfigEntry.Gui.CollapsibleObject
    public SoundSource itemClone = new SoundSource(SoundEvents.ENTITY_ITEM_PICKUP, 0.02f, 2f, 0.1f);
    @ConfigEntry.Gui.CollapsibleObject
    public SoundSource itemDelete = new SoundSource(SoundEvents.BLOCK_COMPOSTER_FILL, 0.75f, 1.5f, 0f);

    public static class SoundSource
    {
        public SoundEvent sound;
        @ConfigEntry.Gui.Tooltip()
        public float volume;
        @ConfigEntry.Gui.Tooltip()
        public float pitch;
        @ConfigEntry.Gui.Tooltip()
        public float pitchRange;

        public SoundSource(SoundEvent sound, float volume, float pitch, float pitchRange)
        {
            this.sound = sound;
            this.volume = volume;
            this.pitch = pitch;
            this.pitchRange = pitchRange;
        }
    }
}
