package moe.nea.molecula.widgets;

import lombok.RequiredArgsConstructor;

import moe.nea.molecula.Molecule;
import moe.nea.molecula.utils.CheckUtil;

@RequiredArgsConstructor
public class MGridPanel extends MLayout {


    final int gridSizeX, gridSizeY, gridGapX, gridGapY;

    public static MGridPanel noGaps(int gridSize) {
        return new MGridPanel(gridSize, gridSize, 0, 0);
    }

    public static MGridPanel withGaps(int gridSize, int gapSize) {
        return new MGridPanel(gridSize, gridSize, gapSize, gapSize);
    }


    /**
     * Add molecule to this grid at the specified coordinates.
     *
     * @param widget the widget to add
     * @param x      the left grid cell index
     * @param y      the right grid cell index
     * @param width  the width in grid cells
     * @param height the height in grid cells
     */
    public <T extends Molecule> T addMolecule(T widget, int x, int y, int width, int height) {
        CheckUtil.failIfLessThan(x, 0, "x");
        CheckUtil.failIfLessThan(y, 0, "y");
        CheckUtil.failIfLessThan(width, 1, "width");
        CheckUtil.failIfLessThan(height, 1, "height");
        widget.setParent(this);
        widget.setLocation(x * (gridSizeX + gridGapX), y * (gridSizeY + gridGapY));
        widget.setSize((width - 1) * (gridSizeX + gridGapX) + gridSizeX,
                       (height - 1) * (gridSizeY + gridGapY) + gridSizeY);
        addChild(widget);
        return widget;
    }

}
