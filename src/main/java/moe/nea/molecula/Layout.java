package moe.nea.molecula;

/**
 * A layout is a {@link Widget} that may contain children. It is bound by all the space allotment rules like a regular
 * {@link Widget}, but also has to allow for adding children via the {@link #addChild} method. A layout does not need to
 * accept any child given, but it should be well documented which child-adding behaviour each layout allows.
 * It is also required that each {@link #render} is also passed along, at least in some capacity, to children. A layout
 * may choose to limit which {@link RenderPass}es are passed along to children. (For example a tabbed layout may decide
 * to only pass {@link RenderPass.Collecting} passes to children unless they are rendered.)
 */
public interface Layout extends Widget {
    /**
     * Add a child to the first available position in this layout.
     *
     * @param widget the widget to add
     */
    void addChild(Widget widget);
}
