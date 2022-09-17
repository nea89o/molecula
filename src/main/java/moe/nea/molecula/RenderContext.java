package moe.nea.molecula;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.renderer.GlStateManager;

@AllArgsConstructor
public class RenderContext {
    private final float mouseX;
    private final float mouseY;
    @Getter
    private final float partialTicks;
    @Getter
    private final MoleculaGui gui;
    private final float offsetX;
    private final float offsetY;
    private final float scaleX;
    private final float scaleY;

    public float getFinalMouseX() {
        return (mouseX - offsetX) * scaleX;
    }

    public float getFinalMouseY() {
        return (mouseY - offsetY) * scaleY;
    }


    /**
     * Test whether the mouse is above an area in pixels, borders inclusive
     *
     * @param left   the left border in pixels
     * @param top    the top border in pixels
     * @param width  the width of the area in pixels
     * @param height the height of the area in pixels
     * @return whether the mouse is within the specified rect
     */
    public boolean isMouseWithin(int left, int top, int width, int height) {
        float mouseX = getFinalMouseX();
        float mouseY = getFinalMouseY();
        return left <= mouseX && mouseX <= (left + width)
                && top <= mouseY && mouseY <= (top + height);
    }

    /**
     * Transforms this {@link RenderContext} to a new one with transformations applied.
     * The original render context is unchanged.
     * Transformations are applied in the order:
     * <ol>
     *    <li>Translation</li>
     *    <li>Scaling</li>
     * </pl>
     *
     * @return a new {@link RenderContext}
     */
    public RenderContext transformed(int offsetX, int offsetY, float scaleX, float scaleY) {
        return new RenderContext(mouseX, mouseY, partialTicks, gui,
                this.offsetX + offsetX * this.scaleX, this.offsetY + offsetY * this.scaleY,
                this.scaleX * scaleX, this.scaleY * scaleY);
    }

    public void applyTransformation() {
        GlStateManager.translate(offsetX, offsetY, 1F);
        GlStateManager.scale(scaleX, scaleY, 1F);
    }
}
