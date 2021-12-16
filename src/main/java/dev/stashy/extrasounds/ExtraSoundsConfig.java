package dev.stashy.extrasounds;

import me.shedaniel.autoconfig.*;
import me.shedaniel.autoconfig.annotation.*;

@Config(name = "extrasounds")
public class ExtraSoundsConfig implements ConfigData
{
    public boolean enableHotbarScrollSounds = true;
    public boolean enableItemSounds = true;
    public boolean enableDropSounds = true;
    public boolean enableCreativeInventoryScrollSounds = true;
    public boolean enableChatMessageSounds = true;
    public boolean enableChatMentionSounds = true;
    public boolean enableStatusEffectsSounds = true;
    public boolean enableKeyboardTypingSounds = true;
    public boolean enableInventoryOpeningSounds = true;
}
