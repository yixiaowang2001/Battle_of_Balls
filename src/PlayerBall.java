import java.util.HashMap;
import java.util.List;
import java.util.Random;

import java.util.Iterator;

import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.Point;

public class PlayerBall {

    private static final int CIRCLE_RAIDUS = 20;
    private CanvasWindow canvas;
    private Ellipse circleShape;
    private CircleControl cc;
    private AIBallControl ac;
    private Boolean flag;
    private double resizeValue;

    public PlayerBall(CanvasWindow canvas) {
        cc = new CircleControl(canvas);
        ac = new AIBallControl(canvas);
        this.canvas = canvas;
        create();
    }

    public void create() {
        Color randColor = createRandColor();
        Point startPoint = new Point(canvas.getWidth() * 0.5 - CIRCLE_RAIDUS, canvas.getHeight() * 0.5 - CIRCLE_RAIDUS);
        circleShape = new Ellipse(startPoint.getX(), startPoint.getY(), CIRCLE_RAIDUS * 2, CIRCLE_RAIDUS * 2);
        circleShape.setFillColor(randColor);
        circleShape.setStrokeColor(randColor.darker());
        circleShape.setStrokeWidth(5);
        canvas.add(circleShape);
    }

    public Color createRandColor() {
        Random rand = new Random();
        float[] hsb = Color.RGBtoHSB(rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0,
                null);
        Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        return color;
    }

    private void resizeAIBall() {
        if (flag) {
            circleShape.setSize(circleShape.getWidth() + 10, circleShape.getHeight() + 10);
            flag = false;
        }
    }

    public void collisionCircle(double dx, double dy) {
        Iterator<Circle> itrCir = cc.getCircleList().iterator();
        while (itrCir.hasNext()) {
            Circle cir = itrCir.next();
            cir.getGraphics().moveBy(dx, dy);
            if (circleShape.getCenter().distance(cir.getCtr()) <= circleShape.getHeight() / 2 + cir.getRadius()) {
                canvas.remove(cir.getGraphics());
                itrCir.remove();
                resizeCir();
            }
        }
    }

    public void moveCir(double dx, double dy) {
        Iterator<Circle> itrCir = cc.getCircleList().iterator();
        while (itrCir.hasNext()) {
            Circle cir = itrCir.next();
            cir.getGraphics().moveBy(dx, dy);
        }
    }

    private void resizeCir() {
        circleShape.setSize(Math.sqrt(Math.pow(circleShape.getHeight(), 2) + Math.pow(Circle.CIRCLE_RAIDUS, 2)),
                Math.sqrt(Math.pow(circleShape.getHeight(), 2) + Math.pow(Circle.CIRCLE_RAIDUS, 2)));
        circleShape.setCenter(canvas.getWidth() * 0.5, canvas.getHeight() * 0.5);
    }

    public void collisionBall(double dx, double dy) {
        Iterator<AIBall> itrBall = ac.getBallList().iterator();
        while (itrBall.hasNext()) {
            AIBall ball = itrBall.next();
            ball.getGraphics().moveBy(dx, dy);
            if (circleShape.getCenter().distance(ball.getCtr()) <= circleShape.getHeight() / 2 - ball.getRadius()) {
                canvas.remove(ball.getGraphics());
                itrBall.remove();
            }
        }
    }

    public CircleControl returnCC() {
        return cc;
    }

    public double getDiameter() {
        return circleShape.getHeight();
    }

    public double getArea() {
        return Math.PI * Math.pow(circleShape.getHeight(), 2);
    }

    public double getResizeVal() {
        return resizeValue;
    }
}
