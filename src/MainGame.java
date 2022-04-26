import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

public class MainGame {
    public static final int CANVAS_WIDTH = 1000;
    public static final int CANVAS_HEIGHT = 750;
    public double ballSpeed = 4;

    private CanvasWindow canvas;
    private GameMap map;
    private Button start, menu, quit;
    private int score;
    private GraphicsText scoreBoard1, gameOver, caption;
    private Image window;
    private GraphicsGroup graphicsGroup;
    private PlayerBall pb;
    private boolean isStart, isBound;
    private double offsetX, offsetY;

    public MainGame() {
        canvas = new CanvasWindow("Test", CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    public void run() {
        menu = new Button("Back to Menu");
        start = new Button("New Game");
        quit = new Button("Quit");
        graphicsGroup = new GraphicsGroup();
        isStart = false;
        resetGame();

        start.onClick(() -> {
            canvas.removeAll();
            startGame();
            menu.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.95);
            canvas.add(menu);
        });

        quit.onClick(() -> {
            canvas.closeWindow();
        });

        menu.onClick(() -> {
            resetGame();
        });

        inGame();

        canvas.animate(() -> {
            if (isStart) {
                if (pb.getArea() > 20000) {
                    endGame();
                }
            }
        });
    }

    private void createMap() {
        map = new GameMap();
        canvas.add(map.getGraphcs());
        // graphicsGroup.add(map.getGraphcs());
        map.getGraphcs().setCenter(0.5 * CANVAS_WIDTH, 0.5 * CANVAS_HEIGHT);
        // canvas.add(graphicsGroup);
    }

    private void createPB() {
        pb = new PlayerBall(canvas);
    }

    private void resetGame() {

        isBound = false;
        offsetX = 0;
        offsetY = 0;

        // graphicsGroup.removeAll();
        canvas.removeAll();
        isStart = false;

        window = new Image("Background.jpg");
        canvas.add(window);

        caption = new GraphicsText("BATTLE OF BALLS");
        caption.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0675);
        caption.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.4);
        canvas.add(caption);

        canvas.add(start);
        start.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8375);

        canvas.add(quit);
        quit.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8875);

        score = 0;
    }

    private void startGame() {
        createMap();
        createPB();
        isStart = true;
    }

    private void inGame() {

        canvas.onMouseMove(event -> {
            if (isStart) {
                double cos = (event.getPosition().getX() - canvas.getCenter().getX())
                        / event.getPosition().distance(canvas.getCenter());
                double sin = (event.getPosition().getY() - canvas.getCenter().getY())
                        / event.getPosition().distance(canvas.getCenter());
                if (event.getPosition() != canvas.getCenter()) {
                    double moveX = -cos * ballSpeed;
                    double moveY = -sin * ballSpeed;
                    isBound = false;
                    if ((offsetX + moveX < -5 * CANVAS_WIDTH + pb.getDiameter() / 2 ||
                            offsetX > 5 * CANVAS_WIDTH - pb.getDiameter() / 2)) {
                        moveX = 0;
                        isBound = true;
                    }
                    if ((offsetY + moveY <= -5 * CANVAS_HEIGHT + pb.getDiameter() / 2 ||
                            offsetY >= 5 * CANVAS_HEIGHT - pb.getDiameter() / 2)) {
                        moveY = 0;
                        isBound = true;
                    }
                    offsetX += moveX;
                    offsetY += moveY;

                    pb.collisionCircle(moveX, moveY);
                    map.getGraphcs().moveBy(moveX, moveY);
                    // System.out.println(map.getGraphcs().getX());

                    if (isBound) {
                        // 判断bound在哪
                        // 下：
                    }

                    pb.returnCC().controlNum(offsetX, offsetY);
                }
            }
        });
    }

    /**
     * Ends the game when one of the players reaches the winning point.
     */
    private void endGame() {
        isStart = false;

        gameOver = new GraphicsText("Game Over! Your score is: " + (int)pb.getArea());
        gameOver.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.05);
        gameOver.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.45);
        canvas.add(gameOver);

        menu.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8375);
        canvas.add(menu);

        quit.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8875);
        canvas.add(quit);
    }

    private Point boundWhere() {

    }

    public static void main(String[] args) {
        MainGame game = new MainGame();
        game.run();
    }
}
