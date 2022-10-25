package moe.nea.molecula;

import javax.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import moe.nea.molecula.utils.AreaUtil;
import moe.nea.molecula.widgets.MLayout;

public abstract class Molecule {
    @Nullable
    @Getter
    @Setter
    MLayout parent;
    @Getter
    int x;
    @Getter
    int y;
    @Getter
    int width;
    @Getter
    int height;

    @Getter
    private MoleculaGui host;

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width  = width;
        this.height = height;
    }

    public int getAbsoluteX() {
        return x + ((parent == null) ? 0 : parent.getAbsoluteX());
    }

    public int getAbsoluteY() {
        return y + ((parent == null) ? 0 : parent.getAbsoluteY());
    }

    /**
     * @param mouseX       relative to the element position
     * @param mouseY       relative to the element position
     * @param partialTicks percentage of time passed till the next tick
     */
    public abstract void render(int mouseX, int mouseY, float partialTicks);

    /**
     * @param mouseX relative to the element
     * @param mouseY relative to the element
     * @return whether the mouse is above this molecule
     */
    public boolean isHovered(int mouseX, int mouseY) {
        return AreaUtil.isWithin(mouseX, mouseY, 0, 0, width, height);
    }

    public void attach(MoleculaGui moleculaGui) {
        host = moleculaGui;
    }

    /**
     * Called when a click event is received.
     * This is called regardless if the element is hovered or not.
     *
     * @param mouseX      relative to the element
     * @param mouseY      relative to the element
     * @param mouseButton mouse button as provided by LWJGL
     */
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }
}
