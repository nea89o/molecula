package moe.nea.molecula;

import java.io.IOException;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

@RequiredArgsConstructor
public class MoleculaGui extends GuiScreen {
    @NonNull
    @Getter
    final Molecule root;

    @Getter int guiLeft, guiTop;

    @Getter
    @Nullable
    Molecule focussed;


    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        root.attach(this);
        guiLeft = (width - root.getWidth()) / 2;
        guiTop  = (height - root.getHeight()) / 2;
        root.setLocation(guiLeft, guiTop);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        GlStateManager.pushMatrix();
        GlStateManager.translate(guiLeft, guiTop, 0);
        root.render(mouseX - guiLeft, mouseY - guiTop, partialTicks);
        GlStateManager.popMatrix();
    }

    public void requestFocus(Molecule molecule) {
        this.focussed = molecule;
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (focussed == null || !focussed.keyTyped(typedChar, keyCode)) {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        root.tick();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        focussed = null;
        root.mouseClicked(mouseX - guiLeft, mouseY - guiTop, mouseButton);
    }
}
