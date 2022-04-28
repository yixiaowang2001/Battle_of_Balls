import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Point;

public class AIBallControl {

    private static final int LOWER_BOUND = 20;
    private static final int UPPER_BOUND = 30;

    private CanvasWindow canvas;
    private List<AIBall> ballList;

    public AIBallControl(CanvasWindow canvas) {
        this.canvas = canvas;
        ballList = new ArrayList<>();

    }

    public void initialize() {
        for (int i = 0; i < 20; i++) {
            AIBall ball = new AIBall(canvas);
            canvas.add(ball.getGraphics());
            canvas.add(ball.getGraphicsName());
            ballList.add(ball);
        }
    }

    public void controlNum(double offsetX, double offsetY) {
        if (ballList.size() < LOWER_BOUND) {
            for (int i = 0; i < randInBound() - ballList.size(); i++) {
                AIBall ball = new AIBall(canvas);
                ball.getGraphics().moveBy(offsetX, offsetY);
                canvas.add(ball.getGraphics());
                ballList.add(ball);
            }
        }
    }

    private int randInBound() {
        Random rand = new Random();
        return rand.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;
    }

    private void resize() {

    }

    public List<AIBall> getBallList() {
        return ballList;
    }

}
