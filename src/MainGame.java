import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

public class MainGame {
    public static final int CANVAS_WIDTH = 1000;
    public static final int CANVAS_HEIGHT = 750;

    private CanvasWindow canvas;
    private GameMap map;
    private Button start, menu, quit;
    private int rank;
    private GraphicsText scoreBoard, gameOver, caption;
    private Image window;
    private PlayerBall pb;
    private AIBall ai;
    private boolean isStart, isBound;
    private double offsetX, offsetY;
    private AIBallControl ac;
    private CircleControl cc;
    private Queue<Ball> rankQueue;

    public MainGame() {
        canvas = new CanvasWindow("Test", CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    public void run() {
        menu = new Button("Back to Menu");
        start = new Button("New Game");
        quit = new Button("Quit");
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
                if (pb.getArea() > 200000) {
                    endGame();
                }
            }
        });
    }

    private void createMap() {
        map = new GameMap();
        canvas.add(map.getGraphcs());
        map.getGraphcs().setCenter(0.5 * CANVAS_WIDTH, 0.5 * CANVAS_HEIGHT);
    }

    private void createPB() {

        cc = new CircleControl(canvas);
        cc.initialize();
        pb = new PlayerBall(canvas);
    }

    private void creatAI() {
        ac = new AIBallControl(canvas);
        ac.initialize();

        ai = new AIBall(canvas);
    }

    private void resetGame() {
        isBound = false;
        offsetX = 0;
        offsetY = 0;

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

        rank = 0;
    }

    private void startGame() {
        createMap();
        createPB();
        creatAI();
        isStart = true;
    }

    private void inGame() {
        canvas.onMouseMove(event -> {
            if (isStart) {
                pb.updateSpeed();
                double cos = (event.getPosition().getX() - canvas.getCenter().getX())
                        / event.getPosition().distance(canvas.getCenter());
                double sin = (event.getPosition().getY() - canvas.getCenter().getY())
                        / event.getPosition().distance(canvas.getCenter());
                if (event.getPosition() != canvas.getCenter()) {
                    double moveX = -cos * pb.getSpeed();
                    double moveY = -sin * pb.getSpeed();
                    isBound = false;
                    if ((offsetX + moveX < -5 * CANVAS_WIDTH + pb.getDiameter() / 2 + 5 ||
                            offsetX + moveX > 5 * CANVAS_WIDTH - pb.getDiameter() / 2 - 5)) {
                        moveX = 0;
                        isBound = true;
                        System.out.println("X好碰！");
                    }
                    if ((offsetY + moveY <= -5 * CANVAS_HEIGHT + pb.getDiameter() / 2 + 5 ||
                            offsetY + moveY >= 5 * CANVAS_HEIGHT - pb.getDiameter() / 2 - 5)) {
                        moveY = 0;
                        isBound = true;
                        System.out.println("Y好碰！");
                    }
                    offsetX += moveX;
                    offsetY += moveY;

                    map.getGraphcs().moveBy(moveX, moveY);
                    pb.collisionCircle(moveX, moveY, cc);
                    pb.collisionBall(moveX, moveY, ac);
                    ai.collisionAiBall(ac);

                    ifHitBound();

                    cc.controlNum(offsetX, offsetY);
                }
            }
        });
    }

    /**
     * Ends the game when one of the players reaches the winning point.
     */
    private void endGame() {
        isStart = false;

        gameOver = new GraphicsText("Game Over! Your score is: " + (int) pb.getArea());
        gameOver.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.05);
        gameOver.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.45);
        canvas.add(gameOver);

        menu.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8375);
        canvas.add(menu);

        quit.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8875);
        canvas.add(quit);
    }

    private void ifHitBound() {
        if (isBound) {
            List<Integer> boundSide = boundWhere();
            double moveDisX = 0;
            double moveDisY = 0;
            if (boundSide.get(0) == 1) {
                moveDisX = map.getGraphcs().getX() + pb.getDiameter() / 2 - 500;
                cc.moveCir(-moveDisX, 0);
                map.getGraphcs().moveBy(-moveDisX, 0);
                offsetX -= moveDisX;
            } else if (boundSide.get(0) == 2) {
                moveDisX = -9500 + pb.getDiameter() / 2 - map.getGraphcs().getX();
                cc.moveCir(moveDisX, 0);
                map.getGraphcs().moveBy(moveDisX, 0);
                offsetX += moveDisX;
            }
            if (boundSide.get(1) == 1) {
                moveDisY = map.getGraphcs().getY() + pb.getDiameter() / 2 - 375;
                cc.moveCir(0, -moveDisY);
                map.getGraphcs().moveBy(0, -moveDisY);
                offsetY -= moveDisY;
            } else if (boundSide.get(1) == 2) {
                moveDisY = -7125 + pb.getDiameter() / 2 - map.getGraphcs().getY();
                cc.moveCir(0, moveDisY);
                map.getGraphcs().moveBy(0, moveDisY);
                offsetY += moveDisY;
            }
        }
    }

    /**
     * Be called if the player ball hit the bound. Detect which bound collides the
     * ball. 0 represents no collision, 1 represents the left one or upper one, and
     * 2 represents the right one or bottom one.
     * 
     * @return
     */
    private List<Integer> boundWhere() {
        List<Integer> retList = new ArrayList<>();
        retList.add(0);
        retList.add(0);
        if (map.getGraphcs().getX() - (-9500) < pb.getDiameter() / 2) {
            retList.set(0, 2);
        } else if (500 - map.getGraphcs().getX() < pb.getDiameter() / 2) {
            retList.set(0, 1);
        }
        if (map.getGraphcs().getY() - (-7125) < pb.getDiameter() / 2) {
            retList.set(1, 2);
        } else if (375 - map.getGraphcs().getY() < pb.getDiameter() / 2) {
            retList.set(1, 1);
        }
        return retList;
    }

    public PlayerBall getPb() {
        return pb;
    }

    public static void main(String[] args) {
        MainGame game = new MainGame();
        game.run();
    }
}
