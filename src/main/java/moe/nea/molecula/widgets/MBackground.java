package moe.nea.molecula.widgets;

import juuxel.libninepatch.NinePatch;
import net.minecraft.util.ResourceLocation;

import moe.nea.molecula.Molecule;
import moe.nea.molecula.Resources;
import moe.nea.molecula.utils.GLPatchRenderer;

public class MBackground extends MLayout {
    private static final NinePatch<ResourceLocation> backgroundPatch =
        NinePatch.builder(Resources.BACKGROUND)
                 .cornerSize(4)
                 .cornerUv(4F / 64)
                 .mode(NinePatch.Mode.STRETCHING)
                 .build();

    public MBackground(int margin, Molecule molecule) {
        addChild(molecule);
        molecule.setLocation(margin, margin);
        setSize(molecule.getWidth() + margin * 2,
                molecule.getHeight() + margin * 2);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        backgroundPatch.draw(GLPatchRenderer.INSTANCE, getWidth(), getHeight());
        super.render(mouseX, mouseY, partialTicks);
    }
}
