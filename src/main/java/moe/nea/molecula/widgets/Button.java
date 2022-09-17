package moe.nea.molecula.widgets;

import juuxel.libninepatch.NinePatch;
import juuxel.libninepatch.TextureRegion;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import moe.nea.molecula.RenderPass;
import moe.nea.molecula.Resources;
import moe.nea.molecula.SizeConstraint;
import moe.nea.molecula.Widget;
import moe.nea.molecula.utils.GLPatchRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

@RequiredArgsConstructor
@Getter
public class Button implements Widget {
    private final String label;
    public final int TEXT_MARGIN = 10;
    private SizeConstraint usedSize = null;

    private static NinePatch<ResourceLocation> createPatch(int offset) {
        return NinePatch
                .builder(new TextureRegion<>(Resources.BUTTONS, 0F, offset / 3F, 1F, (offset + 1) / 3F))
                .mode(NinePatch.Mode.TILING)
                .cornerSize(2, 3)
                .cornerUv(2F / 200F, 3F / 30F)
                .build();
    }

    private static NinePatch<ResourceLocation> backgroundDisabled = createPatch(0);
    private static NinePatch<ResourceLocation> backgroundEnabled = createPatch(1);
    private static NinePatch<ResourceLocation> backgroundHoveredEnabled = createPatch(2);

    public String getTranslatedText() {
        return I18n.format(label);
    }

    @Override
    public SizeConstraint occupy(SizeConstraint available) {
        return (usedSize = available.shrinkTill(
                Minecraft.getMinecraft().fontRendererObj.getStringWidth(getTranslatedText()) + TEXT_MARGIN * 2,
                Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + TEXT_MARGIN * 2));
    }

    @Override
    public void render(RenderPass renderPass) {
        renderPass.renderInPhase(RenderPass.GUI_CONTENT, ctx -> {
            GlStateManager.color(1F, 1F, 1F, 1F);
            NinePatch<ResourceLocation> usedPatch = backgroundEnabled;
            if (ctx.isMouseWithin(0, 0, usedSize.getWidth(), usedSize.getHeight())) {
                usedPatch = backgroundHoveredEnabled;
            }
            usedPatch.draw(GLPatchRenderer.INSTANCE, usedSize.getWidth(), usedSize.getHeight());
            GlStateManager.translate(TEXT_MARGIN, TEXT_MARGIN, 0);
            Minecraft.getMinecraft().fontRendererObj.drawString("§a" + getTranslatedText(), 0, 0, 0xFFFFFFFF);
        });
    }
}
