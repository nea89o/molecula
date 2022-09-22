package moe.nea.molecula;

import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public abstract class MoleculaGui extends GuiScreen {

    protected abstract ScreenDefinition createScreenDefinition();

    private volatile ScreenDefinition screenDefinition;

    public ScreenDefinition getScreenDefinition() {
        if (screenDefinition == null) {
            synchronized (this) {
                if (screenDefinition == null) {
                    screenDefinition = createScreenDefinition();
                }
            }
        }
        return screenDefinition;
    }

    @Override
    public void initGui() {
        getScreenDefinition().prepareGui(new SizeConstraint(width, height));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        getScreenDefinition().renderGui(new RenderContext(mouseX, mouseY, partialTicks, this, 0F, 0F, 1F, 1F));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        getScreenDefinition().onClick(mouseX, mouseY, mouseButton);
    }
}
