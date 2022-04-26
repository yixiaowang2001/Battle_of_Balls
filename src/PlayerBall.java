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
    private Ellipse ballShape;
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
        ballShape = new Ellipse(startPoint.getX(), startPoint.getY(), CIRCLE_RAIDUS * 2, CIRCLE_RAIDUS * 2);
        ballShape.setFillColor(randColor);
        ballShape.setStrokeColor(randColor.darker());
        ballShape.setStrokeWidth(5);
        canvas.add(ballShape);
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
            ballShape.setSize(ballShape.getWidth() + 10, ballShape.getHeight() + 10);
            flag = false;
        }
    }

    public void collisionCircle(double dx, double dy) {
        Iterator<Circle> itrCir = cc.getCircleList().iterator();
        while (itrCir.hasNext()) {
            Circle cir = itrCir.next();
            cir.getGraphics().moveBy(dx, dy);
            if (ballShape.getCenter().distance(cir.getCtr()) <= ballShape.getHeight() / 2 + cir.getRadius()) {
                resizeCir();
                canvas.remove(cir.getGraphics());
                itrCir.remove();
            }
        }
    }

    public void collisionBall(double dx, double dy) {
        Iterator<AIBall> itrBall = ac.getBallList().iterator();
        while (itrBall.hasNext()) {
            AIBall ball = itrBall.next();
            // ball.autoMove();
            ball.getGraphics().moveBy(dx, dy);
            if (ballShape.getCenter().distance(ball.getCtr()) <= Math.abs(getDiameter() / 2 - ball.getRadius())) {
                if (getDiameter() / 2 > ball.getRadius()) {
                    System.out.println("牛逼！");
                    resizeBall(ball);
                    canvas.remove(ball.getGraphics());
                    itrBall.remove();
                } else {
                    // 被吃
                    System.out.println("废物！");
                }
            }
        }
    }

    private void resizeCir() {
        ballShape.setSize(Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(Circle.CIRCLE_RAIDUS, 2)),
                Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(Circle.CIRCLE_RAIDUS, 2)));
        ballShape.setCenter(canvas.getWidth() * 0.5, canvas.getHeight() * 0.5);
    }

    private void resizeBall(AIBall aiBall) {
        double resizeRate = 1;
        ballShape.setSize(ballShape.getWidth() + aiBall.getRadius() * resizeRate, ballShape.getWidth() + aiBall.getRadius() * resizeRate);
        ballShape.setCenter(canvas.getWidth() * 0.5, canvas.getHeight() * 0.5);
    }

    public CircleControl returnCC() {
        return cc;
    }

    public AIBallControl returnAC() {
        return ac;
    }

    public double getDiameter() {
        return ballShape.getHeight();
    }

    public double getArea() {
        return Math.PI * Math.pow(ballShape.getHeight(), 2);
    }

    public double getResizeVal() {
        return resizeValue;
    }
}
