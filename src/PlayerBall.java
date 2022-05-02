import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;

/**
 * The ball that is controlled by the player
 */
public class PlayerBall extends Ball {
    private static final int CIRCLE_RAIDUS = 30;
    private Ellipse ballShape;
    private double resizeValue;
    private String name;

    /**
     * The constructor of the player ball
     * 
     * @param canvas
     */
    public PlayerBall(CanvasWindow canvas) {
        super(canvas);
    }

    /**
     * The method that lets the playerball to eat the cicles and grow.
     * 
     * @param dx,dy let the circle move with the map.
     * @param cc    the list of the cicles.
     */
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

    /**
     * The method that lets the playerball to eat the other balls and grow, or be eaten and ends the
     * game.
     * 
     * @param dx,dy let the AI balls move with the map.
     * @param ac    the list of the AI balls.
     */
    public boolean collisionBall(double dx, double dy, AIBallControl ac, List<Ball> rankList) {
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
                    rankList.remove(ball);
                    itrBall.remove();
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Ball growing method
     * 
     * @param aiBall the ball that needs to be resized.
     */
    private void resizeBall(AIBall aiBall) {
        ballShape.setSize(Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(aiBall.getRadius(), 2)),
            Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(aiBall.getRadius(), 2)));
        ballShape.setCenter(canvas.getWidth() * 0.5, canvas.getHeight() * 0.5);
    }

    /**
     * updates the speed of the ball
     */
    public void updateSpeed() {
        speed = 100 * 1 / ballShape.getHeight() + 1.2;
    }

    /**
     * get the speed of the ball
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * get the area of the ball
     * 
     * @return the area of the ball
     */
    public double getArea() {
        return Math.PI * Math.pow(ballShape.getHeight(), 2);
    }

    /**
     * get the name of the ball
     * 
     * @return the name of the ball
     */
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

    @Override
    public String toString() {
        return "PlayerBall [ballShape=" + ballShape + ", name=" + name + ", resizeValue=" + resizeValue + "]";
    }
}
