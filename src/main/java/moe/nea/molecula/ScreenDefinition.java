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
            RenderPass.Executing executing = new RenderPass.Executing(renderPassKey, renderContext);
            root.render(executing);
        }
    }
}
