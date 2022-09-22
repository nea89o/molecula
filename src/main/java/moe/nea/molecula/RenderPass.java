package moe.nea.molecula;

import lombok.*;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public abstract class RenderPass {

    public static final Key WORLD_BACKGROUND = new Key(-1000);
    public static final Key GUI_BACKGROUND = new Key(-100);
    public static final Key GUI_CONTENT = new Key(0);
    public static final Key HOVER_LAYER = new Key(1000);


    @AllArgsConstructor
    @Getter
    @ToString
    @EqualsAndHashCode
    public static class Key implements Comparable<Key> {
        private int priority;

        @Override
        public int compareTo(@NotNull RenderPass.Key o) {
            return Integer.compare(priority, o.priority);
        }
    }

    private RenderPass() {
    }

    @ToString
    public static class Collecting extends RenderPass {
        @Getter
        private final Set<Key> knownRenderPhases = new HashSet<>();

        @Override
        public void renderInPhase(Key renderPhase, Consumer<RenderContext> runIf) {
            knownRenderPhases.add(renderPhase);
        }

        public List<Key> collect() {
            List<Key> l = new ArrayList<>(knownRenderPhases);
            Collections.sort(l);
            return l;
        }
    }

    @AllArgsConstructor
    @ToString
    public static class Clicking extends RenderPass {
        private final MouseContext mouseContext;

        @Override
        public void onClick(Consumer<MouseContext> clickHandled) {
            clickHandled.accept(mouseContext);
        }

        @Override
        public RenderPass transformed(int offsetX, int offsetY, float scaleX, float scaleY) {
            return new Clicking(mouseContext.transformed(offsetX, offsetY, scaleX, scaleY));
        }
    }

    @RequiredArgsConstructor
    @ToString
    public static class Rendering extends RenderPass {
        @Getter
        private final Key currentPhase;
        private final RenderContext renderContext;

        @Override
        public RenderPass transformed(int offsetX, int offsetY, float scaleX, float scaleY) {
            return new Rendering(currentPhase, renderContext.transformed(offsetX, offsetY, scaleX, scaleY));
        }

        @Override
        public void renderInPhase(Key renderPhase, Consumer<RenderContext> runIf) {
            if (renderPhase == currentPhase) {
                GlStateManager.pushMatrix();
                renderContext.applyTransformation();
                runIf.accept(renderContext);
                GlStateManager.popMatrix();
            }
        }
    }

    public RenderPass transformed(int offsetX, int offsetY, float scaleX, float scaleY) {
        return this;
    }

    public RenderPass transformed(int offsetX, int offsetY) {
        return transformed(offsetX, offsetY, 1F, 1F);
    }

    public void renderInPhase(Key renderPhase, Consumer<RenderContext> runIf) {
    }

    public void onClick(Consumer<MouseContext> clickHandled) {
    }

}
