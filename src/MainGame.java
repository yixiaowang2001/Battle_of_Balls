import java.util.List;
import java.util.Random;
import java.awt.Color;

import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

public class MainGame {
    public static final int CANVAS_WIDTH = 1000;
    public static final int CANVAS_HEIGHT = 750;
    public static final int BALL_SPEED = 10;

    private CanvasWindow canvas;
    private GameMap map;
    private Button start, menu, quit;
    private int score;
    private GraphicsText scoreBoard1, scoreBoard2, gameOver, caption;
    private Image window;
    private GraphicsGroup graphicsGroup;

    public MainGame() {
        canvas = new CanvasWindow("Test", CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    public void run() {
        menu = new Button("Back to Menu");
        start = new Button("Start Game");
        quit = new Button("Quit");
        graphicsGroup = new GraphicsGroup();
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
    }

    private void resetGame() {
        canvas.removeAll();

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

    }

    private void inGame() {
        
        canvas.onMouseMove(event -> {
            double cos = (event.getPosition().getX() - canvas.getCenter().getX()) / event.getPosition().distance(canvas.getCenter());
            double sin = (event.getPosition().getY() - canvas.getCenter().getY()) / event.getPosition().distance(canvas.getCenter());
            graphicsGroup.moveBy(-cos * BALL_SPEED, -sin * BALL_SPEED);
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
