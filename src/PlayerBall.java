import java.util.Iterator;
import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;

public class PlayerBall extends Ball {
    private static final int CIRCLE_RAIDUS = 30;
    private Ellipse ballShape;
    private double resizeValue;
    private String name;

    public PlayerBall(CanvasWindow canvas) {
        super(canvas);
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
            if (isCollision(ballShape.getCenter(), ball.getCtr(), getRadius(), ball.getRadius(), 0.85)) {
                if (getRadius() > ball.getRadius()) {
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

    public double getArea() {
        return Math.PI * Math.pow(ballShape.getHeight(), 2);
    }

    public double getResizeVal() {
        return resizeValue;
    }

    @Override
    void create() {
        name = "Player";
        Color randColor = createRandColor();
        Point startPoint = new Point(canvas.getWidth() * 0.5 - CIRCLE_RAIDUS, canvas.getHeight() * 0.5 - CIRCLE_RAIDUS);
        ballShape = new Ellipse(startPoint.getX(), startPoint.getY(), CIRCLE_RAIDUS * 2, CIRCLE_RAIDUS * 2);
        ballShape.setFillColor(randColor);
        ballShape.setStrokeColor(randColor.darker());
        ballShape.setStrokeWidth(5);
        canvas.add(ballShape);
    }

    @Override
    void resizeCir() {
        ballShape.setSize(Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(Circle.CIRCLE_RAIDUS * 2, 2)),
                Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(Circle.CIRCLE_RAIDUS * 2, 2)));
        ballShape.setCenter(canvas.getWidth() * 0.5, canvas.getHeight() * 0.5);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getRadius() {
        return ballShape.getHeight() / 2;
    }

    @Override
    public GraphicsObject getGraphics() {
        return ballShape;
    }
}
