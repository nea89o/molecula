package moe.nea.molecula;

import lombok.Data;
import moe.nea.molecula.utils.CheckUtil;

@Data
public class SizeConstraint {
    private final int width, height;

    public SizeConstraint(int width, int height) {
        CheckUtil.failIfLessThan(width, 0, "width");
        CheckUtil.failIfLessThan(height, 0, "height");
        this.width = width;
        this.height = height;
    }

    public SizeConstraint shrinkTill(int width, int height) {
        return new SizeConstraint(Math.min(width, this.width), Math.min(height, this.height));
    }

    public boolean isContainedWithin(SizeConstraint sizeConstraint) {
        return this.width <= sizeConstraint.width && this.height <= sizeConstraint.height;
    }
}
