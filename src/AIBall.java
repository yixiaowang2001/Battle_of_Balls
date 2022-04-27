import java.util.List;
import java.util.Iterator;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Point;
import java.awt.Color;

public class AIBall extends Ball {
    private Ellipse ballShape;
    private GraphicsText nameText;
    private CanvasWindow canvas;
    private double radius, moveSpeed, randCos, randSin;
    private int moveCount;
    private Color color;
    private String name;

    public AIBall(CanvasWindow canvas) {
        this.canvas = canvas;
        radius = createRandRadius();
        Point randPoint = createRandPos();
        color = createRandColor();
        name = createRandName();
        moveSpeed = 0;
        moveCount = 0;
        randCos = 0;
        randSin = 0;

        ballShape = new Ellipse(randPoint.getX(), randPoint.getY(), radius, radius);
        ballShape.setFillColor(color);
        ballShape.setStrokeColor(color.darker());
        ballShape.setStrokeWidth(5);

        nameText = new GraphicsText(name);

        nameText.setFontSize(radius * 0.4);
        nameText.setCenter(ballShape.getCenter());

    }

    public void autoMove(double offsetX, double offsetY) {
        updateBound();
        updateSpeed();
        Random rand = new Random();
        if (moveCount == 0) {
            randCos = -1 + 2 * rand.nextDouble();
            randSin = -1 + 2 * rand.nextDouble();
            ballShape.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
            nameText.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
            moveCount = 500;
        } else {
            ballShape.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
            nameText.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
            moveCount--;
        }
    }

    private void updateBound() {

    }

    private void updateSpeed() {
        moveSpeed = 100 * 1 / (getRadius() * 2) + 0.8;
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
        double randR = 100 * (rand.nextDouble()) + 5 * Circle.CIRCLE_RAIDUS;
        return randR;
    }

    private String createRandName() {
        StringBuilder sb = new StringBuilder();
        List<String> namelist = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        Random rand = new Random();
        int length = 3 + rand.nextInt(3);
        for (int i = 0; i < length; i++) {
            String str = namelist.get(rand.nextInt(namelist.size()));
            sb.append(str);
        }
        return sb.toString();
    }

    public void collisionAiBall(AIBallControl ac) {
        Iterator<AIBall> itrBall = ac.getBallList().iterator();
        while (itrBall.hasNext()) {
            AIBall ball = itrBall.next();
            if (ballShape.getCenter().distance(ball.getCtr()) <= Math.abs(getRadius() - ball.getRadius())) {
                if (getRadius() > ball.getRadius()) {
                    System.out.println("x");
                    resizeBall(ball.getBall());
                    canvas.remove(ball.getGraphics());
                    canvas.remove(ball.getGraphicsName());
                    itrBall.remove();

                }
            }
        }
    }

    private void resizeBall(Ellipse otherBall) {
        double resizeRate = 1;

        ballShape.setSize(ballShape.getWidth() + otherBall.getHeight() / 2 * resizeRate,
                ballShape.getWidth() + otherBall.getHeight() / 2 * resizeRate);

    }

    private Ellipse getBall() {
        return ballShape;
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

    public GraphicsText getGraphicsName() {
        return nameText;
    }
}
