import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

import edu.macalester.graphics.CanvasWindow;

public class AIBallControl {

    private static final int LOWER_BOUND = 15;
    private static final int UPPER_BOUND = 16;

    private CanvasWindow canvas;
    private Queue<AIBall> ballQueue;
    private List<Ball> rankList;

    public AIBallControl(CanvasWindow canvas, List<Ball> rankList) {
        this.canvas = canvas;
        this.rankList = rankList;
        ballQueue = new ConcurrentLinkedDeque<>();
    }

    public void initialize() {
        for (int i = 0; i < 20; i++) {
            AIBall ball = new AIBall(canvas);
            canvas.add(ball.getGraphics());
            canvas.add(ball.getGraphicsName());
            ballQueue.add(ball);
            rankList.add(ball);
        }
    }

    public void controlNum(double offsetX, double offsetY) {
        if (ballQueue.size() < LOWER_BOUND) {
            for (int i = 0; i < randInBound() - ballQueue.size(); i++) {
                AIBall ball = new AIBall(canvas);
                ball.getGraphics().moveBy(offsetX, offsetY);
                canvas.add(ball.getGraphics());
                canvas.add(ball.getGraphicsName());
                ballQueue.add(ball);
                rankList.add(ball);
            }
        }
    }

    private int randInBound() {
        Random rand = new Random();
        return rand.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;
    }

    public Queue<AIBall> getBallQueue() {
        return ballQueue;
    }

}
