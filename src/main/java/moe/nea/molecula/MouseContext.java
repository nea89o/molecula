package moe.nea.molecula;


import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MouseContext {
    private int mouseButton;
    private float mouseX, mouseY;
    private float scaleX, scaleY;

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
        return left <= mouseX && mouseX <= (left + width)
                && top <= mouseY && mouseY <= (top + height);
    }


    public MouseContext transformed(int offsetX, int offsetY, float scaleX, float scaleY) {
        return new MouseContext(
                mouseButton,
                mouseX - offsetX / this.scaleX,
                mouseY - offsetY / this.scaleY,
                this.scaleX * scaleX,
                this.scaleY * scaleY
        );
    }
}
