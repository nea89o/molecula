package moe.nea.molecula;

/**
 * A widget is something that can be rendered in a {@link MoleculaGui}.
 * Each widget is responsible for requesting a certain amount of display space and should only draw within that display
 * space. The next highest {@link Layout} is responsible for positioning, so each widget (including
 * Layouts) can assume that the top left of the allotted space is at 0, 0.
 */
public interface Widget {


    /**
     * Occupy is called by Molecula every time a relayout is performed (for example: in case of a screen size update).
     * The called Widget should save the available size and try to fill it as good as possible.
     * {@link Layout Layouts} should propagate that call to children in some way.
     *
     * @param available the maximum space available for this widget
     * @return the minimum size that this layout needs
     */
    void occupy(SizeConstraint available);

    /**
     * Render in accordance with the specified render pass.
     * To do this use {@link RenderPass#renderInPhase} along with {@link RenderPass.Key}. Do not do conditional logic
     * outside the {@link RenderPass#renderInPhase} callback, as that might influence the RenderPass calculator and
     * prevent part of your GUI from being rendered at all.
     */
    void render(RenderPass renderPass);
}
