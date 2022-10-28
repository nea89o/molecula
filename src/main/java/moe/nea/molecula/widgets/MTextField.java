package moe.nea.molecula.widgets;

import java.util.function.BiPredicate;
import juuxel.libninepatch.NinePatch;
import juuxel.libninepatch.TextureRegion;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import moe.nea.molecula.Molecule;
import moe.nea.molecula.Resources;
import moe.nea.molecula.utils.GLPatchRenderer;

public class MTextField extends Molecule {

    private static NinePatch<ResourceLocation> background = createPatch(0);
    private static NinePatch<ResourceLocation> backgroundSelected = createPatch(1);

    @Getter
    @Setter
    IChatComponent placeholder = new ChatComponentText("Placeholder");

    /**
     * Gets invoked whenever the text gets changed.
     * Return false from this to deny this change.
     * <b>N.B.: if you deny partial matches, it may be impossible for end users to type in a fully correct result</b>
     */
    @Getter
    @Setter
    BiPredicate<String, String> onChange;

    @Getter
    @Setter
    String text = "";

    @Getter
    @Setter
    FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

    private int tick = 0;

    private static NinePatch<ResourceLocation> createPatch(int offset) {
        return NinePatch
            .builder(new TextureRegion<>(Resources.TEXTFIELD, 0F, offset / 2F, 1F, (offset + 1) / 2F))
            .mode(NinePatch.Mode.TILING)
            .cornerSize(2, 2)
            .cornerUv(2F / 200F, 2F / 30F)
            .build();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        NinePatch<ResourceLocation> usedPatch;
        if (isFocussed()) {
            usedPatch = backgroundSelected;
        } else {
            usedPatch = background;
        }
        GlStateManager.color(1F, 1F, 1F, 1F);
        usedPatch.draw(GLPatchRenderer.INSTANCE, getWidth(), getHeight());
        int    margin       = (getHeight() - font.FONT_HEIGHT) / 2;
        int    textColor    = text.isEmpty() ? 0xFF808080 : 0xFFFFFFFF;
        String renderedText = text.isEmpty() ? placeholder.getFormattedText() : text;
        font.drawString(renderedText, margin, margin, textColor);
        GlStateManager.color(1F, 1F, 1F, 1F);
        if (tick < 10 && isFocussed()) {
            int cursorPos = font.getStringWidth(text);
            invertedRect(cursorPos + margin, margin - 1, 1, font.FONT_HEIGHT + 2);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            requestFocus();
            // TODO: set cursor
        }
    }

    @Override
    public void tick() {
        tick = (tick + 1) % 20;
    }

    @Override
    public boolean keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_BACK) {
            setTextWithCheck(text.substring(0, Math.max(0, text.length() - 1)));
            return true;
        }
        if (keyCode == Keyboard.KEY_ESCAPE) {
            getHost().requestFocus(null);
            return true;
        }
        if (typedChar < 0x20 || !Character.isDefined(typedChar)) return false;
        setTextWithCheck(text + typedChar);
        return true;
    }

    public void setTextWithCheck(String newText) {
        if (onChange != null && !onChange.test(text, newText)) return;
        text = newText;
    }

    private void invertedRect(int x, int y, int width, int height) {
        var tesselator = Tessellator.getInstance();
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();


        GlStateManager.colorLogicOp(GL11.GL_OR_REVERSE);
        var renderer = tesselator.getWorldRenderer();
        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        renderer.pos(x, y + height, 0).endVertex();
        renderer.pos(x + width, y + height, 0).endVertex();
        renderer.pos(x + width, y, 0).endVertex();
        renderer.pos(x, y, 0).endVertex();
        tesselator.draw();

        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }
}
