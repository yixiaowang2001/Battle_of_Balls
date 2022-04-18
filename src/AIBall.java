import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.Point;

public class AIBall {

    private static final double CIRCLE_RAIDUS = 20;
    private CanvasWindow canvas;
    private double posX, posY;
    private Ellipse circleShape;
    private Boolean flag;
    public ArrayList<Double> aiBallRadius;
    public ArrayList<Point> aiBallPoint;
    private int count = 0;
    private Color nColor;

    public AIBall(CanvasWindow canvas) {
        this.canvas = canvas;
        posX = 0;
        posY = 0;
        create();
    }

    public void create() {
        Point randPoint = createRandPos();
        circleShape = new Ellipse(randPoint.getX(), randPoint.getY(), CIRCLE_RAIDUS, CIRCLE_RAIDUS);
        circleShape.setFillColor(createRandColor());
        circleShape.setStroked(true);
        circleShape.setStrokeColor(nColor);
        canvas.add(circleShape);
        aiBallRadius.add(count, CIRCLE_RAIDUS);
        aiBallPoint.add(count, randPoint);
        count++;
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
        nColor = Color.getHSBColor((float) (hsb[0] * 0.8), (float) (hsb[1] * 0.8), (float) (hsb[2] * 0.8));
        return color;
    }

    private void move() {
        Random rand = new Random();
        double dx = rand.nextDouble() * 5;
        double dy = rand.nextDouble() * 5;
        circleShape.moveBy(dx, dy);
        aiBallPoint.add(count, new Point(circleShape.getX() + dx, circleShape.getY() + dy));
    }

    private void resize() {
        if (flag) {
            circleShape.setSize(getDiameter() + 10, getDiameter() + 10);
            aiBallRadius.add(count, (getDiameter() + 10) / 2);
            flag = false;
        }
    }

    public double getDiameter() {
        return circleShape.getHeight();
    }
}
