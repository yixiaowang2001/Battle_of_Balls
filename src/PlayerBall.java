import java.util.Random;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;

public class PlayerBall implements Ball {

    private static final int CIRCLE_RAIDUS = 30;
    private double speed;
    private CanvasWindow canvas;
    private Ellipse ballShape;
    private Boolean flag;
    private double resizeValue;
    private String name;

    public PlayerBall(CanvasWindow canvas) {

        this.canvas = canvas;
        name = "Player";
        speed = 0;
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

    public void collisionCircle(double dx, double dy, CircleControl cc) {
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

    public boolean collisionBall(double dx, double dy, AIBallControl ac) {
        Iterator<AIBall> itrBall = ac.getBallQueue().iterator();
        while (itrBall.hasNext()) {
            AIBall ball = itrBall.next();
            ball.getGraphics().moveBy(dx, dy);
            ball.getGraphicsName().moveBy(dx, dy);
            if (isCollision(ballShape.getCenter(), ball.getCtr(), getDiameter() / 2, ball.getRadius(), 0.85)) {
                if (getDiameter() / 2 > ball.getRadius()) {
                    resizeBall(ball);
                    canvas.remove(ball.getGraphics());
                    canvas.remove(ball.getGraphicsName());
                    itrBall.remove();
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCollision(Point ctr1, Point ctr2, double r1, double r2, double rate) {
        double dis = ctr1.distance(ctr2);
        PriorityQueue<Double> pq = new PriorityQueue<>();
        pq.add(r1);
        pq.add(r2);
        double r = pq.poll();
        double R = pq.poll();
        if (r / R <= rate) {
            if (dis <= R) {
                return true;
            }
        }
        return false;
    }

    private void resizeCir() {
        ballShape.setSize(Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(Circle.CIRCLE_RAIDUS, 2)),
                Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(Circle.CIRCLE_RAIDUS, 2)));
        ballShape.setCenter(canvas.getWidth() * 0.5, canvas.getHeight() * 0.5);
    }

    private void resizeBall(AIBall aiBall) {
        ballShape.setSize(Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(aiBall.getRadius(), 2)),
                Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(aiBall.getRadius(), 2)));
        ballShape.setCenter(canvas.getWidth() * 0.5, canvas.getHeight() * 0.5);
    }

    public void updateSpeed() {
        speed = 100 * 1 / ballShape.getHeight() + 1.2;
    }

    public double getSpeed() {
        return speed;
    }

    // public CircleControl returnCC() {
    // return cc;
    // }

    // public AIBallControl returnAC() {
    // return ac;
    // }

    public double getDiameter() {
        return ballShape.getHeight();
    }

    public double getArea() {
        return Math.PI * Math.pow(ballShape.getHeight(), 2);
    }

    public double getResizeVal() {
        return resizeValue;
    }

    public String getName() {
        return name;
    }

    @Override
    public double getRadius() {
        return ballShape.getHeight() / 2;
    }

    public GraphicsObject getGraphics() {
        return ballShape;
    }
}
