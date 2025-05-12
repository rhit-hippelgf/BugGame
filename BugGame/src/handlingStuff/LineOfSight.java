package handlingStuff;

public class LineOfSight {
    public static boolean hasLineOfSight(int x0, int y0, int x1, int y1, char[][] layout, int tileSize) {
        double px = x0;
        double py = y0;
        double dx = x1 - x0;
        double dy = y1 - y0;
        double distance = Math.hypot(dx, dy);

        // direction normalize
        double stepX = dx / distance;
        double stepY = dy / distance;

        // detect transparency along ray
        for (int i = 0; i <= distance; i++) {
            int tileX = (int) (px / tileSize);
            int tileY = (int) (py / tileSize);

            if (!inBounds(tileX, tileY, layout) || !isTransparent(layout[tileY][tileX])) {
                return false;
            }

            px += stepX;
            py += stepY;
        }

        return true;
    }

    private static boolean isTransparent(char tile) {
        return tile == '.' || tile == 'T'; // adjust based on your floor tile types! Here . and T are transparent 
    }

    private static boolean inBounds(int x, int y, char[][] layout) {
        return y >= 0 && y < layout.length && x >= 0 && x < layout[0].length;
    }
}

//    TTTTTTTTTTTTT
//    TTTTTTTTTT.TT
//    TTTTBTTTTTTTT
//    TTTTTTFTTTTTT
//    TTTT.TTTTTTTT
//    TTTTTTTTFTTTT
//    TTTTTTTTTTTTT
//    . and F are transparent

