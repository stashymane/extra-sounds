package dev.stashy.extrasounds.ui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class SoundMixerScreen extends CottonClientScreen
{

    public SoundMixerScreen(GuiDescription description)
    {
        super(new TranslatableText("options.sounds.title"), description);
    }

    @Override
    protected void reposition(int screenWidth, int screenHeight)
    {
        WPanel root = description.getRootPanel();
        this.left = (screenWidth - root.getWidth()) / 2;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks)
    {
        super.render(matrices, mouseX, mouseY, partialTicks);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
    }

    @Override
    public void onClose()
    {
        super.onClose();
        MinecraftClient.getInstance().options.write();
    }
}
