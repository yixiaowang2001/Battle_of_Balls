import java.util.Random;

import edu.macalester.graphics.*;
import java.awt.Color;

public class Circle {

    private CanvasWindow canvas;

    private static final double CIRCLE_RAIDUS = 10;

    private Ellipse circleShape;

    public Circle(CanvasWindow canvas) {
        this.canvas = canvas;
    }

    public void addToCanvas() {
        Point randPoint = createRandPos();
        circleShape = new Ellipse(randPoint.getX(), randPoint.getY(), CIRCLE_RAIDUS, CIRCLE_RAIDUS);
        circleShape.setFillColor(createRandColor());
        circleShape.setStroked(false);
        canvas.add(circleShape);
    }

    private Point createRandPos() {
        Random rand = new Random();
        double minX = -4.5 * canvas.getWidth() + CIRCLE_RAIDUS;
        double maxX = 5.5 * canvas.getWidth() - CIRCLE_RAIDUS;
        double minY = -4.5 * canvas.getHeight() + CIRCLE_RAIDUS;
        double maxY = 5.5 * canvas.getWidth() - CIRCLE_RAIDUS;
        double randomX = minX + (maxX - minX) * rand.nextDouble();
        double randomY = minY + (maxY - minY) * rand.nextDouble();
        return new Point(randomX, randomY);
    }

    private Color createRandColor() {
        Random rand = new Random();
        float[] hsb = Color.RGBtoHSB(rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0,
                null);
        Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        return color;
    }

    public void removeFromCanvas() {
        canvas.remove(circleShape);
    }

    public Point getPos() {
        return circleShape.getCenter();
    }

    public Ellipse getShape() {
        return circleShape;
    }

    public double getR() {
        return CIRCLE_RAIDUS;
    }

    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("Test", 1000, 500);
        for (int i = 0; i < 10000; i++) {
            Circle circle = new Circle(canvas);
        }
    }
}
