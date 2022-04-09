import java.util.List;
import java.util.Random;
import java.awt.Color;

import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

/**
 * The game of Tank Trouble
 */
public class TankTroubleGame {
    private static final int CANVAS_WIDTH = 1200;
    private static final int CANVAS_LENGTH = 800;
    
    private CanvasWindow canvas;
    private List<GameMap> maps;
    private GameMap currentMap;
    private Random rand;
    private Button start, quit, menu;
    private int score1, score2;
    private GraphicsText scoreBoard1, scoreBoard2, gameOver, caption;
    private Tank tank1, tank2;
    private GraphicsGroup player1, player2;
    private String winner;
    private Image window;
    private boolean animation;

    /**
     * Creates a game window of fixed size
     */
    public TankTroubleGame() {
        canvas = new CanvasWindow("TankTroubleGame!", CANVAS_WIDTH, CANVAS_LENGTH);
    }

    /**
     * Runs the program.
     */
    private void run() {
        menu = new Button("Back to Menu");
        start = new Button("Start Game");
        quit = new Button("Quit");
        tank1 = new Tank(true, canvas, currentMap);
        tank2 = new Tank(false, canvas, currentMap);

        resetGame();

        start.onClick(() -> {
            canvas.removeAll();
            startGame();
            menu.setCenter(CANVAS_WIDTH / 2, CANVAS_LENGTH * 0.95);
            canvas.add(menu);
            animation = true;
            tank1.setStatus(true);
            tank2.setStatus(true);
        });

        quit.onClick(() -> {
            canvas.closeWindow();
        });

        menu.onClick(() -> {
            if (tank1.getClip().size() == 0 && tank2.getClip().size() == 0) {
                resetGame();
            }
        });

        inGame();
        tank1.fire();
        tank2.fire();
    }

    /**
     * Randomly generates a map from all avaible maps and add it to the canvas.
     */
    private void createMap() {
        maps = List.of(
            new Map1(),
            new Map2(),
            new Map3(),
            new Map4(),
            new Map5());

        rand = new Random();
        int mapIndex = rand.nextInt(maps.size());
        currentMap = maps.get(mapIndex);
        canvas.add(currentMap.getGraphics());
        currentMap.getGraphics().setScale(1 / 1.2);
        currentMap.getGraphics().setCenter(600, 860 / 2.4);
    }

    /**
     * Creates 2 tanks on the canvas.
     */
    private void createTank() {
        canvas.add(tank1.getGraphics());
        canvas.add(tank2.getGraphics());
        tank1.resetPos();
        tank2.resetPos();
    }

    /**
     * Move the ball in the clip. Also do the ball collision.
     */
    private void ballAction(Tank tank) {
        List<Ball> tankClip = tank.getClip();
        Ball tempBall = new Ball(tank.getAngle(), tank.getCenter(), tank.getHeight(), canvas, currentMap);
        for (Ball ball : tankClip) {
            String sta = ball.ballMove(0.05, 300, tank1, tank2);
            if (sta.equals("TIMEOUT")) {
                tempBall = ball;
                tempBall.removeFromCanvas();
            } else if (sta.equals("TANK1") && tank1.getStatus()) {
                tempBall = ball;
                tempBall.removeFromCanvas();
                tank1.removeFromCanvas();
                tank1.setStatus(false);
            } else if (sta.equals("TANK2") && tank2.getStatus()) {
                tempBall = ball;
                tempBall.removeFromCanvas();
                tank2.removeFromCanvas();
                tank2.setStatus(false);
            }
        }
        tankClip.remove(tempBall);
    }

    /**
     * Creates a score board on the canvas
     */
    private void createScoreBoard() {
        scoreBoard1 = new GraphicsText();
        scoreBoard1.setText("Player1: " + score1);
        scoreBoard1.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.02);
        scoreBoard1.setCenter(0.2 * CANVAS_WIDTH, 0.92 * CANVAS_LENGTH);
        canvas.add(scoreBoard1);

        scoreBoard2 = new GraphicsText();
        scoreBoard2.setText("Player2: " + score2);
        scoreBoard2.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.02);
        scoreBoard2.setCenter(0.8 * CANVAS_WIDTH, 0.92 * CANVAS_LENGTH);
        canvas.add(scoreBoard2);

        player1 = new GraphicsGroup();
        Ellipse ellipse1 = new Ellipse(0, 0, 2 * tank1.getRadius(), 2 * tank1.getRadius());
        ellipse1.setFillColor(new Color(244, 184, 68));
        Line line1 = new Line(ellipse1.getCenter().getX(), ellipse1.getCenter().getY(),
            ellipse1.getCenter().getX() + 20, ellipse1.getCenter().getY());
        player1.add(ellipse1);
        player1.add(line1);
        player1.setCenter(0.125 * CANVAS_WIDTH, 0.92 * CANVAS_LENGTH);
        canvas.add(player1);

        player2 = new GraphicsGroup();
        Ellipse ellipse2 = new Ellipse(0, 0, 2 * tank2.getRadius(), 2 * tank2.getRadius());
        ellipse2.setFillColor(new Color(123, 189, 244));
        Line line2 = new Line(ellipse2.getCenter().getX(), ellipse2.getCenter().getY(),
            ellipse2.getCenter().getX() - 20, ellipse2.getCenter().getY());
        player2.add(ellipse2);
        player2.add(line2);
        player2.setCenter(0.875 * CANVAS_WIDTH, 0.92 * CANVAS_LENGTH);
        canvas.add(player2);
    }

    /**
     * The main part of the game.
     */
    private void inGame() {
        canvas.animate(() -> {
            if (animation) {
                tank1.move();
                tank2.move();
                ballAction(tank1);
                ballAction(tank2);
                if (tank1.getClip().size() == 0 && tank2.getClip().size() == 0
                    && (!tank1.getStatus() || !tank2.getStatus())) {
                    nextGame();
                }
                if (score1 >= 5 || score2 >= 5) {
                    endGame();
                }
            }
        });
    }

    /**
     * Resets the state of the game.
     */
    private void resetGame() {
        canvas.removeAll();

        window = new Image("TankTrouble.png");
        window.setScale(1100 / 1440.0, 1100 / 1440.0);
        window.setCenter(CANVAS_WIDTH / 2, CANVAS_LENGTH * 0.475);
        canvas.add(window);

        caption = new GraphicsText("TANK TROUBLE !");
        caption.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.05);
        caption.setCenter(CANVAS_WIDTH / 2, CANVAS_LENGTH * 0.15);
        canvas.add(caption);

        canvas.add(start);
        start.setCenter(CANVAS_WIDTH / 2, CANVAS_LENGTH * 0.8375);

        canvas.add(quit);
        quit.setCenter(CANVAS_WIDTH / 2, CANVAS_LENGTH * 0.8875);

        score1 = 0;
        score2 = 0;

        animation = false;
        tank1.setStatus(false);
        tank2.setStatus(false);
    }

    /**
     * Set up the game.
     */
    private void startGame() {
        createMap();
        createTank();
        createScoreBoard();
    }

    /**
     * Starts the next game when some player dies
     */
    private void nextGame() {
        if (tank1.getStatus() && !tank2.getStatus()) {
            score1 += 1;
        }
        if (tank2.getStatus() && !tank1.getStatus()) {
            score2 += 1;
        }
        scoreBoard1.setText("Player1: " + score1);
        scoreBoard2.setText("Player2: " + score2);
        canvas.remove(currentMap.getGraphics());
        createMap();
        if (!tank1.getStatus()) {
            canvas.add(tank1.getGraphics());
        }
        if (!tank2.getStatus()) {
            canvas.add(tank2.getGraphics());
        }
        tank1.setCrtMap(currentMap);
        tank2.setCrtMap(currentMap);
        tank1.resetPos();
        tank2.resetPos();
        tank1.setStatus(true);
        tank2.setStatus(true);
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
        gameOver.setCenter(CANVAS_WIDTH / 2, CANVAS_LENGTH * 0.45);
        canvas.add(gameOver);

        menu.setCenter(CANVAS_WIDTH / 2, CANVAS_LENGTH * 0.6);
        canvas.add(menu);

        quit.setCenter(CANVAS_WIDTH / 2, CANVAS_LENGTH * 0.65);
        canvas.add(quit);
    }

    @Override
    public String toString() {
        return "TankTroubleGame [animation=" + animation + ", canvas=" + canvas + ", caption=" + caption
            + ", currentMap=" + currentMap + ", gameOver=" + gameOver + ", maps=" + maps + ", menu=" + menu
            + ", player1=" + player1 + ", player2=" + player2 + ", quit=" + quit + ", rand=" + rand + ", score1="
            + score1 + ", score2=" + score2 + ", scoreBoard1=" + scoreBoard1 + ", scoreBoard2=" + scoreBoard2
            + ", start=" + start + ", tank1=" + tank1 + ", tank2=" + tank2 + ", window=" + window + ", winner=" + winner
            + "]";
    }

    public static void main(String[] args) {
        TankTroubleGame game = new TankTroubleGame();
        game.run();
    }
}
