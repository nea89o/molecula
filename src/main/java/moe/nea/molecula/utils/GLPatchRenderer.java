package moe.nea.molecula.utils;

import juuxel.libninepatch.TextureRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public enum GLPatchRenderer implements TextureRenderer<ResourceLocation> {
    INSTANCE;

    @Override
    public void draw(ResourceLocation texture, int x, int y, int width, int height, float u1, float v1, float u2, float v2) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        RenderUtils.drawTexture(width, height, u1, v1, u2, v2);
        GlStateManager.popMatrix();
    }
}
