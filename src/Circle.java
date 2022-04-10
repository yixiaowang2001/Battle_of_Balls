import java.util.List;
import java.util.Random;

import edu.macalester.graphics.*;
import java.awt.Color;

public class Circle {
    
    private CanvasWindow canvas;

    private static final double CIRCLE_RAIDUS = 10;

    private Ellipse circleShape;
    private double posX, posY;

    public Circle(CanvasWindow canvas) {
        this.canvas = canvas;
        posX = 0;
        posY = 0;
        create();
    }

    public void create() {
        Point randPoint = createRandPos(canvas);
        circleShape = new Ellipse(randPoint.getX(), randPoint.getY(), CIRCLE_RAIDUS, CIRCLE_RAIDUS);
        circleShape.setFillColor(createRandColor());
        circleShape.setStroked(false);
        canvas.add(circleShape);
    }

    private Point createRandPos(CanvasWindow canvasWindow) {
        Random rand = new Random();
        double randomX = CIRCLE_RAIDUS + (canvasWindow.getWidth() - CIRCLE_RAIDUS * 2) * rand.nextDouble();
        double randomY = CIRCLE_RAIDUS + (canvasWindow.getHeight() - CIRCLE_RAIDUS * 2) * rand.nextDouble();
        return new Point(randomX, randomY);
    }

    private Color createRandColor() {
        Random rand = new Random();
        float[] hsb = Color.RGBtoHSB(rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0, null);
        Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        return color;
    }

    public double getX() {
        return posX;
    }

    public double getY() {
        return posY;
    }

    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("Test", 1000, 500);
        for (int i = 0; i < 100; i++) {
            Circle circle = new Circle(canvas);
        }
    }
}
