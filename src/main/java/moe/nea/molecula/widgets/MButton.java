package moe.nea.molecula.widgets;

import java.util.function.Supplier;
import juuxel.libninepatch.NinePatch;
import juuxel.libninepatch.TextureRegion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import moe.nea.molecula.Molecule;
import moe.nea.molecula.Resources;
import moe.nea.molecula.utils.GLPatchRenderer;

@Data
@EqualsAndHashCode(callSuper = true)
public class MButton extends Molecule {

    private static NinePatch<ResourceLocation> backgroundDisabled = createPatch(0);
    private static NinePatch<ResourceLocation> backgroundEnabled = createPatch(1);
    private static NinePatch<ResourceLocation> backgroundHoveredEnabled = createPatch(2);
    final Supplier<String> label;
    boolean enabled = true;
    Runnable onClick;

    public static MButton withStaticLabel(String label) {
        return new MButton(() -> label);
    }

    public static MButton withTranslatableLabel(String key, Object... params) {
        return new MButton(() -> I18n.format(key, params));
    }

    private static NinePatch<ResourceLocation> createPatch(int offset) {
        return NinePatch
            .builder(new TextureRegion<>(Resources.BUTTONS, 0F, offset / 3F, 1F, (offset + 1) / 3F))
            .mode(NinePatch.Mode.TILING)
            .cornerSize(2, 3)
            .cornerUv(2F / 200F, 3F / 30F)
            .build();
    }

    public String getTranslatedText() {
        return label.get();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        NinePatch<ResourceLocation> usedPatch;
        if (isEnabled()) {
            if (isHovered(mouseX, mouseY)) {
                usedPatch = backgroundHoveredEnabled;
            } else {
                usedPatch = backgroundEnabled;
            }
        } else {
            usedPatch = backgroundDisabled;
        }
        GlStateManager.color(1F, 1F, 1F, 1F);
        usedPatch.draw(GLPatchRenderer.INSTANCE, getWidth(), getHeight());
        int margin = (getHeight() - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT) / 2;
        Minecraft.getMinecraft().fontRendererObj.drawString(getTranslatedText(), margin, margin, 0xFFFFFFFF);
        GlStateManager.color(1F, 1F, 1F, 1F);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && onClick != null) {
            onClick.run();
        }
    }
}
