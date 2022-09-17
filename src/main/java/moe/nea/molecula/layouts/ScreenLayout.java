package moe.nea.molecula.layouts;

import juuxel.libninepatch.NinePatch;
import lombok.Getter;
import lombok.Setter;
import moe.nea.molecula.*;
import moe.nea.molecula.utils.CheckUtil;
import moe.nea.molecula.utils.GLPatchRenderer;
import moe.nea.molecula.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

@Getter
public class ScreenLayout implements Layout {

    private int guiLeft, guiTop;
    private Widget child;
    /**
     * Insets specify the margin between the border of the rendered gui and the rendered content of the gui.
     * Insets less than 4 can result in content being rendered on the border of the gui.
     */
    @Setter
    private int insets = 6;
    private SizeConstraint size;
    private NinePatch<ResourceLocation> backgroundPatch = NinePatch.builder(Resources.BACKGROUND)
            .cornerSize(4)
            .cornerUv(4F / 64)
            .mode(NinePatch.Mode.STRETCHING)
            .build();

    @Override
    public void addChild(Widget widget) {
        CheckUtil.failUnless(child == null, () -> "There can only be one child added to the base screen layout. Consider adding a column or a grid.");
        child = widget;
    }

    @Override
    public SizeConstraint occupy(SizeConstraint available) {
        CheckUtil.failIf(child == null, () -> "There is no element added to the base screen layout. Consider adding a space.");
        SizeConstraint requested = child.occupy(new SizeConstraint(available.getWidth() - 2 * insets, available.getHeight() - 2 * insets));
        size = new SizeConstraint(requested.getWidth() + 2 * insets, requested.getHeight() + 2 * insets);
        guiLeft = available.getWidth() / 2 - size.getWidth() / 2;
        guiTop = available.getHeight() / 2 - size.getHeight() / 2;
        return size;
    }

    @Override
    public void render(RenderPass renderPass) {
        renderPass.renderInPhase(RenderPass.WORLD_BACKGROUND, ctx -> {
            ctx.getGui().drawDefaultBackground();
        });
        RenderPass backgroundSpace = renderPass.transformed(guiLeft, guiTop);
        backgroundSpace.renderInPhase(RenderPass.GUI_BACKGROUND, ctx -> {
            backgroundPatch.draw(GLPatchRenderer.INSTANCE, size.getWidth(), size.getHeight());
        });
        RenderPass contentSpace = backgroundSpace.transformed(insets, insets);
        child.render(contentSpace);
    }
}
