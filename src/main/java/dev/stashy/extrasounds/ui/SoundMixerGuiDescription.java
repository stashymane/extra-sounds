package dev.stashy.extrasounds.ui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class SoundMixerGuiDescription extends LightweightGuiDescription
{
    Screen parent;

    public SoundMixerGuiDescription(Screen parent)
    {
        this.parent = parent;

        var root = new WGridPanel(9);
        setRootPanel(root);
        root.setSize(254, 220);


        var label = new WLabel(new TranslatableText("options.sounds.title"));
        root.add(label, 0, 0, 28, 2);
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        label.setVerticalAlignment(VerticalAlignment.CENTER);
        label.setColor(0xffffff, 0x333333);

        var categories =
                new WListPanel<>(List.of(SoundCategory.values()),
                                 () -> new WLabeledSlider(0, 100, Axis.HORIZONTAL),
                                 (cat, slider) -> {
                                     slider.setLabel(new TranslatableText("soundCategory." + cat.getName()));
                                     slider.setValue(
                                             (int) (MinecraftClient.getInstance().options.getSoundVolume(cat) * 100));
                                     slider.setValueChangeListener((val) -> {
                                         MinecraftClient.getInstance().options.setSoundVolume(
                                                 cat, val / 100f);
                                         MinecraftClient.getInstance().options.write();
                                     });
                                 });
        categories.setListItemHeight(26);
        root.add(categories, 0, 2, 28, 20);

        WButton back = new WButton(ScreenTexts.DONE);
        back.setAlignment(HorizontalAlignment.CENTER);
        back.setOnClick(() -> {
            MinecraftClient.getInstance().setScreen(parent);
        });

        root.add(back, 3, 22, 22, 1);

        root.validate(this);
    }

    @Override
    public void addPainters()
    {

    }
}
