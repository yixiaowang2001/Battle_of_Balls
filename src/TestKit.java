import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.events.Key;

public class TestKit {
    
    private CanvasWindow canvas;
    private GraphicsGroup gg;
    private PlayerBall pb;

    public static final int CANVAS_WIDTH = 1000;
    public static final int CANVAS_HEIGHT = 750;
    public static final int BALL_SPEED = 5;

    public TestKit() {
        canvas = new CanvasWindow("TestKit", CANVAS_WIDTH, CANVAS_HEIGHT);
        gg = new GraphicsGroup();
    }

    private void run() {
        canvas.animate(() -> {
            if (canvas.getKeysPressed().contains(Key.W)) {

            }
            if (canvas.getKeysPressed().contains(Key.S)) {

            }
        });
    }

    private void initialize() {
        createCir();
        // createPB();
        canvas.add(gg);
    }

    private void createCir() {
        Circle cir = new Circle(canvas);
        gg.add(cir.getShape());
    }

    public static void main(String[] args) {
        TestKit tk = new TestKit();
        tk.initialize();
    }
}
