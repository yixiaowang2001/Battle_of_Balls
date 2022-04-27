import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Point;

public class AIBallControl {
    private CanvasWindow canvas;
    private List<AIBall> ballList;

    public AIBallControl(CanvasWindow canvas) {
        this.canvas = canvas;
        ballList = new ArrayList<>();

    }

    public void initialize() {
        for (int i = 0; i < 200; i++) {
            AIBall ball = new AIBall(canvas);
            canvas.add(ball.getGraphics());
            canvas.add(ball.getGraphicsName());
            ballList.add(ball);
        }
    }


    private void resize() {

    }

    public List<AIBall> getBallList() {
        return ballList;
    }

}
