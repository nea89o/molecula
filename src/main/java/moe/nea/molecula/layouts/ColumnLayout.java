package moe.nea.molecula.layouts;

import lombok.Data;
import lombok.Getter;
import moe.nea.molecula.Layout;
import moe.nea.molecula.RenderPass;
import moe.nea.molecula.SizeConstraint;
import moe.nea.molecula.Widget;

import java.util.ArrayList;
import java.util.List;

public class ColumnLayout implements Layout {

    @Getter
    private final List<Widget> children = new ArrayList<>();

    private final List<LayoutedChild> layoutedChildren = new ArrayList<>();

    @Data
    private static class LayoutedChild {
        final int offsetY;
        final Widget widget;
    }

    @Override
    public void addChild(Widget widget) {
        children.add(widget);
    }

    @Override
    public SizeConstraint occupy(SizeConstraint available) {
        SizeConstraint remaining = available;
        SizeConstraint ourSize = new SizeConstraint(0, 0);
        layoutedChildren.clear();
        for (Widget child : children) {
            SizeConstraint requested = child.occupy(remaining);
            layoutedChildren.add(new LayoutedChild(ourSize.getHeight(), child));
            ourSize = new SizeConstraint(Math.max(ourSize.getWidth(), requested.getWidth()), ourSize.getHeight() + requested.getHeight());
            remaining = new SizeConstraint(remaining.getWidth(), remaining.getHeight() - requested.getHeight());
        }
        return ourSize;
    }

    @Override
    public void render(RenderPass renderPass) {
        for (LayoutedChild layoutedChild : layoutedChildren) {
            layoutedChild.getWidget().render(renderPass.transformed(0, layoutedChild.getOffsetY()));
        }
    }
}
