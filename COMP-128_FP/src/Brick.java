import java.awt.Color;

import edu.macalester.graphics.Rectangle;

/**
 * A brick of a rectangle shape
 */
public class Brick extends Rectangle {

    /**
     * Creates a brick whose upper left is at (x,y) with the specified width and height. The brick is
     * colored in gray with no stroke outline
     */
    public Brick(double x, double y, double width, double height) {
        super(x, y, width, height);
        setFillColor(Color.GRAY);
        setStroked(false);
    }
}
