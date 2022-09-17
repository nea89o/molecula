package moe.nea.molecula.utils;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    public static void drawTexture(
            float renderedWidth, float renderedHeight,
            float lowTexU, float lowTexV,
            float highTexU, float highTexV
    ) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(0, renderedHeight, 0)
                .tex(lowTexU, highTexV)
                .endVertex();
        worldRenderer.pos(renderedWidth, renderedHeight, 0)
                .tex(highTexU, highTexV)
                .endVertex();
        worldRenderer.pos(renderedWidth, 0, 0)
                .tex(highTexU, lowTexV)
                .endVertex();
        worldRenderer.pos(0, 0, 0)
                .tex(lowTexU, lowTexV)
                .endVertex();
        tessellator.draw();
    }

}
