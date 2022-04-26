import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import java.awt.Color;

public class AIBall {
    private Ellipse ballShape;
    private CanvasWindow canvas;
    private double radius, moveSpeed, randCos, randSin;
    private int moveCount;
    private Color color;

    public AIBall(CanvasWindow canvas) {
        this.canvas = canvas;
        radius = createRandRadius();
        Point randPoint = createRandPos();
        color = createRandColor();
        moveSpeed = 0;
        moveCount = 0;
        randCos = 0;
        randSin = 0;
        ballShape = new Ellipse(randPoint.getX(), randPoint.getY(), radius, radius);
        ballShape.setFillColor(color);
        ballShape.setStrokeColor(color.darker());
        ballShape.setStrokeWidth(5);
    }

    public void autoMove(double offsetX, double offsetY) {
        updateBound();
        updateBallSpeed();
        Random rand = new Random();
        if (moveCount == 0) {
            randCos = -1 + 2 * rand.nextDouble();
            randSin = -1 + 2 * rand.nextDouble();
            ballShape.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
            moveCount = 500;
        } else {
            ballShape.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
            moveCount--;
        }
    }

    private void updateBound() {
        
    }

    private void updateBallSpeed() {
        moveSpeed = 50 * 1 / (getRadius() * 2) + 1.6;
    }

    private Point createRandPos() {
        Random rand = new Random();
        double minX = -4.5 * canvas.getWidth() + radius;
        double maxX = 5.5 * canvas.getWidth() - radius;
        double minY = -4.5 * canvas.getHeight() + radius;
        double maxY = 5.5 * canvas.getWidth() - radius;
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

    private double createRandRadius() {
        Random rand = new Random();
        double randR = 150 * (rand.nextDouble()) + 2 * Circle.CIRCLE_RAIDUS;
        return randR;
    }

    public double getRadius() {
        return ballShape.getHeight() / 2;
    }

    public GraphicsObject getGraphics() {
        return ballShape;
    }

    public Point getCtr() {
        return ballShape.getCenter();
    }
}
