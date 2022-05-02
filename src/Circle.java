import java.awt.Color;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.Point;

/**
 * The resource of the game that can be eaten by balls.
 */
public class Circle {
    static final double CIRCLE_RAIDUS = 5;
    private CanvasWindow canvas;
    private Ellipse circleShape;

    /**
     * The constructor of the circle
     * 
     * @param canvas the canvaswindow
     */
    public Circle(CanvasWindow canvas) {
        this.canvas = canvas;
        Point randPoint = createRandPos();
        circleShape = new Ellipse(randPoint.getX(), randPoint.getY(), CIRCLE_RAIDUS * 2, CIRCLE_RAIDUS * 2);
        circleShape.setFillColor(createRandColor());
        circleShape.setStroked(false);
    }

    /**
     * Creates a random position
     * 
     * @return a random position
     *
     */
    private Point createRandPos() {
        Random rand = new Random();
        double minX = -4.5 * canvas.getWidth() + CIRCLE_RAIDUS;
        double maxX = 5.5 * canvas.getWidth() - CIRCLE_RAIDUS;
        double minY = -4.5 * canvas.getHeight() + CIRCLE_RAIDUS;
        double maxY = 5.5 * canvas.getHeight() - CIRCLE_RAIDUS;
        double randomX = minX + (maxX - minX) * rand.nextDouble();
        double randomY = minY + (maxY - minY) * rand.nextDouble();
        return new Point(randomX, randomY);
    }

    /**
     * Creates a random color
     * 
     * @return a random color
     *
     */
    private Color createRandColor() {
        Random rand = new Random();
        float[] hsb = Color.RGBtoHSB(rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0,
            null);
        Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        return color;
    }

    /**
     * get the center of the ball
     * 
     * @return the center of the ball
     */
    public Point getCtr() {
        return circleShape.getCenter();
    }

    /**
     * get the shape of the ball
     * 
     * @return the shape of the ball
     */
    public Ellipse getGraphics() {
        return circleShape;
    }

    /**
     * get the radiu of the ball
     * 
     * @return the radius of the ball
     */
    public double getRadius() {
        return CIRCLE_RAIDUS;
    }

    @Override
    public String toString() {
        return "Circle [canvas=" + canvas + ", circleShape=" + circleShape + "]";
    }
}
