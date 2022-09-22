package moe.nea.molecula;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import moe.nea.molecula.layouts.ScreenLayout;
import moe.nea.molecula.utils.CheckUtil;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ScreenDefinition {
    private final ScreenLayout root;

    private List<RenderPass.Key> requiredRenderPasses = null;

    public void createRenderPass() {
        RenderPass.Collecting collector = new RenderPass.Collecting();
        getRoot().render(collector);
        requiredRenderPasses = collector.collect();
    }


    public void prepareGui(SizeConstraint sizeConstraint) {
        SizeConstraint requestedSize = root.occupy(sizeConstraint);
        CheckUtil.failUnless(requestedSize.isContainedWithin(sizeConstraint),
                () -> "the size " + requestedSize + " requested by " + root + " is bigger than the maximum size of " + sizeConstraint);
        createRenderPass();
    }

    public void renderGui(RenderContext renderContext) {
        for (RenderPass.Key renderPassKey : requiredRenderPasses) {
            RenderPass.Rendering rendering = new RenderPass.Rendering(renderPassKey, renderContext);
            root.render(rendering);
        }
    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {
        RenderPass.Clicking clicking = new RenderPass.Clicking(new MouseContext(mouseButton, mouseX, mouseY, 1F, 1F));
        root.render(clicking);
    }
}
