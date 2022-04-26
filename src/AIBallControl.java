import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.Point;

public class AIBallControl {

    private static final double CIRCLE_RAIDUS = 20;
    private CanvasWindow canvas;
    private Ellipse circleShape;
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
            ballList.add(ball);
        }
    }

    public void moveAIBall(double dx, double dy) {
        Iterator<AIBall> itrBall = ballList.iterator();
        while (itrBall.hasNext()) {
            AIBall ball = itrBall.next();
            ball.getGraphics().moveBy(dx, dy);
        }
        
    }

    private void resize() {
        
    }

    public List<AIBall> getBallList() {
        return ballList;
    }
}
