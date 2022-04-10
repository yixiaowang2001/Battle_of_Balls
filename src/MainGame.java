import java.util.List;
import java.util.Random;
import java.awt.Color;

import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

public class MainGame {
    public static final int CANVAS_WIDTH = 1000;
    public static final int CANVAS_HEIGHT = 500;

    private CanvasWindow canvas;
    private GameMap map;
    private Button start, menu, quit;
    private int score1, score2;
    private GraphicsText scoreBoard1, scoreBoard2, gameOver, caption;
    private String winner;
    // canvas
    // hex & ball collision
    // UserInterface
    
    public MainGame() {
        canvas = new CanvasWindow("Test", 2000, 1000);
    }

    public void run() {
        menu = new Button("Back to Menu");
        start = new Button("Start Game");
        quit = new Button("Quit");

        resetGame();
    }

    private void createMap() {
        map = new GameMap();
        canvas.add(map.getGraphcs());
    }

    private void resetGame() {
        canvas.removeAll();

        canvas.add(start);
        start.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8375);

        canvas.add(quit);
        quit.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8875);

        score1 = 0;
        score2 = 0;
    }

    private void startGame() {
        createMap();

    }

    private void inGame() {
        canvas.animate(() -> {
            
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
        if (score1 > score2) {
            winner = "Player 1";
        } else {
            winner = "Player 2";
        }

        gameOver = new GraphicsText("Game Over! " + winner + " wins!");
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
