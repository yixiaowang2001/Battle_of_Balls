import java.util.List;
import java.util.Random;
import java.awt.Color;

import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

public class MainGame {
    public static final int CANVAS_WIDTH = 1000;
    public static final int CANVAS_HEIGHT = 750;
    public double ballSpeed = 3;

    private CanvasWindow canvas;
    private GameMap map;
    private Button start, menu, quit;
    private int score;
    private GraphicsText scoreBoard1, scoreBoard2, gameOver, caption;
    private Image window;
    private GraphicsGroup graphicsGroup;
    private PlayerBall pb;
    private boolean isStart;
    private double offsetX, offsetY;

    public MainGame() {
        canvas = new CanvasWindow("Test", CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    public void run() {
        menu = new Button("Back to Menu");
        start = new Button("Start Game");
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
    }

    private void createMap() {
        map = new GameMap();
        graphicsGroup.add(map.getGraphcs());
        map.getGraphcs().setCenter(0.5 * CANVAS_WIDTH, 0.5 * CANVAS_HEIGHT);
    }

    private void createPB() {
        pb = new PlayerBall(canvas);
    }

    private void resetGame() {
        canvas.removeAll();
        graphicsGroup.removeAll();

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
        canvas.add(graphicsGroup);
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
                    if ((offsetX + moveX < -5 * CANVAS_WIDTH + pb.getDiameter() / 2 ||
                            offsetX > 5 * CANVAS_WIDTH - pb.getDiameter() / 2)) {
                        moveX = 0;
                    }
                    if ((offsetY + moveY <= -5 * CANVAS_HEIGHT + pb.getDiameter() / 2 ||
                            offsetY >= 5 * CANVAS_HEIGHT - pb.getDiameter() / 2)) {
                        moveY = 0;
                    }
                    offsetX += moveX;
                    offsetY += moveY;
                    graphicsGroup.moveBy(moveX, moveY);
                    pb.collisionCircle(moveX, moveY);
                    pb.returnCC().controlNum(offsetX, offsetY);
                }
            }
        });
    }

    /**
     * Starts the next game when some player dies
     */
    private void nextGame() {

    }

    /**
     * Ends the game when one of the players reaches the winning point.
     */
    private void endGame() {
        canvas.removeAll();

        gameOver = new GraphicsText("Game Over! Your score is: " + score);
        gameOver.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.06);
        gameOver.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.45);
        canvas.add(gameOver);

        menu.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.6);
        canvas.add(menu);

        quit.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.65);
        canvas.add(quit);
    }

    public static void main(String[] args) {
        MainGame game = new MainGame();
        game.run();
    }
}
