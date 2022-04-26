import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.Point;

public class AIBallManage {

    private static final double CIRCLE_RAIDUS = 20;
    private CanvasWindow canvas;
    private Ellipse circleShape;
    private Boolean flag;
    private List<Double> aiBallRadius;
    private List<Point> aiBallPoint;
    private List<AIBall> balls;
    private int count;

    public AIBallManage(CanvasWindow canvas) {
        this.canvas = canvas;
        aiBallRadius = new ArrayList<>();
        aiBallPoint = new ArrayList<>();
        balls = new ArrayList<>();
        count = 0;
        create();
    }

    private void create() {
        for (int i = 0; i < 200; i++) {
            AIBall ball = new AIBall(canvas);
            canvas.add(ball.getGraphics());
            balls.add(ball);
            count++;
        }
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
