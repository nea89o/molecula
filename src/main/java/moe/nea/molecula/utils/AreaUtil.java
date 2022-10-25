package moe.nea.molecula.utils;

public class AreaUtil {
    public static boolean isWithin(int posX, int posY, int left, int top, int width, int height) {
        posX -= left;
        posY -= top;
        return 0 <= posX && posX < width &&
                0 <= posY && posY < height;
    }
}
