import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;

public class AIBallControl {

    private static final double CIRCLE_RAIDUS = 20;
    private CanvasWindow canvas;
    private Boolean flag;
    private List<AIBall> ballList;


    public AIBallControl(CanvasWindow canvas) {
        this.canvas = canvas;
        ballList = new ArrayList<>();
        initialize();
    }

    private void initialize() {
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
