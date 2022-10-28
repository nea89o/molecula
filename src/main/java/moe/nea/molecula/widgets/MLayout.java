package moe.nea.molecula.widgets;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import net.minecraft.client.renderer.GlStateManager;

import moe.nea.molecula.MoleculaGui;
import moe.nea.molecula.Molecule;

public abstract class MLayout extends Molecule {

    @Getter
    List<Molecule> children = new ArrayList<>();

    protected void addChild(Molecule molecule) {
        children.add(molecule);
        setSize(Math.max(getWidth(), molecule.getWidth() + molecule.getX()),
                Math.max(getHeight(), molecule.getHeight() + molecule.getY()));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        for (Molecule child : getChildren()) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(child.getX(), child.getY(), 0);
            child.render(mouseX - child.getX(), mouseY - child.getY(), partialTicks);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Molecule child : getChildren()) {
            child.mouseClicked(mouseX - child.getX(), mouseY - child.getY(), mouseButton);
        }
    }

    @Override
    public void tick() {
        for (Molecule child : getChildren()) {
            child.tick();
        }
    }

    @Override
    public void attach(MoleculaGui moleculaGui) {
        super.attach(moleculaGui);
        for (Molecule child : getChildren()) {
            child.attach(moleculaGui);
        }
    }
}
