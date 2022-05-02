import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

import edu.macalester.graphics.CanvasWindow;

/**
 * Initialize the AiBalls and create the list to store them.
 */
public class AIBallControl {
    private static final int LOWER_BOUND = 15;
    private static final int UPPER_BOUND = 25;

    private CanvasWindow canvas;
    private Queue<AIBall> ballQueue;
    private List<Ball> rankList;

    /**
     * The constructor of the AiBalls
     * @param canvas The canvas that displays the ball
     * @param rankList The leaderboard of the AIBalls
     */
    public AIBallControl(CanvasWindow canvas, List<Ball> rankList) {
        this.canvas = canvas;
        this.rankList = rankList;
        ballQueue = new ConcurrentLinkedDeque<>();
    }

    /**
     * Create a new AIBall and add it to the list.
     */
    public void initialize() {
        for (int i = 0; i < 9; i++) {
            AIBall ball = new AIBall(canvas);
            canvas.add(ball.getGraphics());
            canvas.add(ball.getGraphicsName());
            ballQueue.add(ball);
            rankList.add(ball);
        }
    }

    /**
     * Controls the number of the Ai balls.
     * 
     * @param offsetX,offsetY prevent the balls from moving out of the boundry
     */
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

    /**
     * get a random int
     * 
     * @return a random int
     */
    private int randInBound() {
        Random rand = new Random();
        return rand.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;
    }

    /**
     * get the list of the balls
     * 
     * @return the list of the balls
     */
    public Queue<AIBall> getBallQueue() {
        return ballQueue;
    }

    @Override
    public String toString() {
        return "AIBallControl [ballQueue=" + ballQueue + ", canvas=" + canvas + ", rankList=" + rankList + "]";
    }

}
