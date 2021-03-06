import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.Button;

/**
 * The main game class that creates the game and runs it.
 * 
 * @author Joseph, Eric, Dennis
 */
public class MainGame {
    static final int CANVAS_WIDTH = 1000;
    static final int CANVAS_HEIGHT = 750;

    private CanvasWindow canvas;
    private GameMap map;
    private Button start, menu, quit;
    private int rank;
    private GraphicsText leaderBoard, gameOver, gameRank, caption, rank1, rank2, rank3, rank4, rank5, rank6, rank7,
        rank8, rank9, rank10, rankPlayer;
    private Image window;
    private PlayerBall pb;
    private boolean isStart, isBound;
    private double offsetX, offsetY;
    private AIBallControl ac;
    private CircleControl cc;
    private List<Ball> rankList;
    private Rectangle board;

    /**
     * The constructor of the main game
     */
    public MainGame() {
        canvas = new CanvasWindow("Battle of Balls", CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    /**
     * Runs the program
     */
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
                if (pb.getArea() > 100000000) {
                    endGame();
                }
            }
        });
    }

    /**
     * Creates the map
     */
    private void createMap() {
        map = new GameMap();
        canvas.add(map.getGraphcs());
        map.getGraphcs().setCenter(0.5 * CANVAS_WIDTH, 0.5 * CANVAS_HEIGHT);
    }

    /**
     * Creates the player ball
     */
    private void createPB() {
        cc = new CircleControl(canvas);
        cc.initialize();
        pb = new PlayerBall(canvas);
    }

    /**
     * Creates the AI balls
     */
    private void creatAI() {
        ac = new AIBallControl(canvas, rankList);
        ac.initialize();
    }

    /**
     * Creates the UIs
     */
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

    /**
     * Creates the game
     */
    private void startGame() {
        rankList = new ArrayList<>();
        createMap();
        createPB();
        creatAI();

        rankList.add(pb);
        Collections.sort(rankList, new SizeComparator());
        createLeaderBoard();

        isStart = true;
    }

    /**
     * Action when the player moves the mouse
     */
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
                    if ((offsetX + moveX < -5 * CANVAS_WIDTH + pb.getRadius() - 2 ||
                        offsetX + moveX > 5 * CANVAS_WIDTH - pb.getRadius() + 2)) {
                        moveX = 0;
                        isBound = true;
                    }
                    if ((offsetY + moveY <= -5 * CANVAS_HEIGHT + pb.getRadius() - 2 ||
                        offsetY + moveY >= 5 * CANVAS_HEIGHT - pb.getRadius() + 2)) {
                        moveY = 0;
                        isBound = true;
                    }
                    offsetX += moveX;
                    offsetY += moveY;

                    map.getGraphcs().moveBy(moveX, moveY);

                    pb.collisionCircle(moveX, moveY, cc);
                    boolean end = pb.collisionBall(moveX, moveY, ac, rankList);
                    if (end) {
                        canvas.remove(pb.getGraphics());
                        endGame();
                    }

                    Iterator<AIBall> ballItr = ac.getBallQueue().iterator();
                    while (ballItr.hasNext()) {
                        AIBall ball = ballItr.next();
                        ball.autoMove(offsetX, offsetY);
                        ball.collisionAiBall(ac, rankList);
                        ball.collisionCircle(cc);
                    }

                    ac.controlNum(offsetX, offsetY);
                    cc.controlNum(offsetX, offsetY);

                    ifHitBound();
                }
                updateLeaderBoard();
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

        gameRank = new GraphicsText("Your rank is: " + rank);
        gameRank.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.05);
        gameRank.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.495);
        canvas.add(gameRank);

        menu.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8375);
        canvas.add(menu);

        quit.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8875);
        canvas.add(quit);
    }

    /**
     * If the player hits the bound, the map will move to the opposite direction.
     */
    private void ifHitBound() {
        if (isBound) {
            List<Integer> boundSide = boundWhere();
            double moveDisX = 0;
            double moveDisY = 0;
            if (boundSide.get(0) == 1) {
                moveDisX = map.getGraphcs().getX() + pb.getRadius() - 500;
                cc.moveCir(-moveDisX, 0);
                map.getGraphcs().moveBy(-moveDisX, 0);
                offsetX -= moveDisX;
            } else if (boundSide.get(0) == 2) {
                moveDisX = -9500 + pb.getRadius() - map.getGraphcs().getX();
                cc.moveCir(moveDisX, 0);
                map.getGraphcs().moveBy(moveDisX, 0);
                offsetX += moveDisX;
            }
            if (boundSide.get(1) == 1) {
                moveDisY = map.getGraphcs().getY() + pb.getRadius() - 375;
                cc.moveCir(0, -moveDisY);
                map.getGraphcs().moveBy(0, -moveDisY);
                offsetY -= moveDisY;
            } else if (boundSide.get(1) == 2) {
                moveDisY = -7125 + pb.getRadius() - map.getGraphcs().getY();
                cc.moveCir(0, moveDisY);
                map.getGraphcs().moveBy(0, moveDisY);
                offsetY += moveDisY;
            }
        }
    }

    /**
     * Creates the leader board
     */
    private void createLeaderBoard() {
        for (int i = 0; i < rankList.size(); i++) {
            if (rankList.get(i) == pb) {
                rank = i + 1;
            }
        }

        board = new Rectangle(0.8 * CANVAS_WIDTH, 0, 0.2 * CANVAS_WIDTH, 0.35 * CANVAS_HEIGHT);
        Color color = new Color(0, 0, 0, 100);
        board.setFillColor(color);
        board.setStroked(false);
        canvas.add(board);

        leaderBoard = new GraphicsText("Leader Board");
        leaderBoard.setFillColor(Color.white);
        leaderBoard.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.025);
        leaderBoard.setCenter(board.getCenter().getX(), board.getHeight() * 0.085);
        canvas.add(leaderBoard);

        rank1 = new GraphicsText(String.format("1. %s", rankList.get(0).getName()));
        rank1.setFillColor(Color.white);
        rank1.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0175);
        rank1.setPosition(board.getX() + 10, 55);
        canvas.add(rank1);

        rank2 = new GraphicsText(String.format("2. %s", rankList.get(1).getName()));
        rank2.setFillColor(Color.white);
        rank2.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0175);
        rank2.setPosition(board.getX() + 10, 75);
        canvas.add(rank2);

        rank3 = new GraphicsText(String.format("3. %s", rankList.get(2).getName()));
        rank3.setFillColor(Color.white);
        rank3.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0175);
        rank3.setPosition(board.getX() + 10, 95);
        canvas.add(rank3);

        rank4 = new GraphicsText(String.format("4. %s", rankList.get(3).getName()));
        rank4.setFillColor(Color.white);
        rank4.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0175);
        rank4.setPosition(board.getX() + 10, 115);
        canvas.add(rank4);

        rank5 = new GraphicsText(String.format("5. %s", rankList.get(4).getName()));
        rank5.setFillColor(Color.white);
        rank5.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0175);
        rank5.setPosition(board.getX() + 10, 135);
        canvas.add(rank5);

        rank6 = new GraphicsText(String.format("6. %s", rankList.get(5).getName()));
        rank6.setFillColor(Color.white);
        rank6.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0175);
        rank6.setPosition(board.getX() + 10, 155);
        canvas.add(rank6);

        rank7 = new GraphicsText(String.format("7. %s", rankList.get(6).getName()));
        rank7.setFillColor(Color.white);
        rank7.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0175);
        rank7.setPosition(board.getX() + 10, 175);
        canvas.add(rank7);

        rank8 = new GraphicsText(String.format("8. %s", rankList.get(7).getName()));
        rank8.setFillColor(Color.white);
        rank8.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0175);
        rank8.setPosition(board.getX() + 10, 195);
        canvas.add(rank8);

        rank9 = new GraphicsText(String.format("9. %s", rankList.get(8).getName()));
        rank9.setFillColor(Color.white);
        rank9.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0175);
        rank9.setPosition(board.getX() + 10, 215);
        canvas.add(rank9);

        rank10 = new GraphicsText(String.format("10. %s", rankList.get(9).getName()));
        rank10.setFillColor(Color.white);
        rank10.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0175);
        rank10.setPosition(board.getX() + 10, 235);
        canvas.add(rank10);

        rankPlayer = new GraphicsText(String.format("%d. %s", rank, pb.getName()));
        rankPlayer.setFillColor(Color.blue);
        rankPlayer.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0175);
        rankPlayer.setPosition(board.getX() + 10, 255);
        canvas.add(rankPlayer);

    }

    /**
     * Updates the leader board
     */
    private void updateLeaderBoard() {
        Collections.sort(rankList, new SizeComparator());
        for (int i = 0; i < rankList.size(); i++) {
            if (rankList.get(i) == pb) {
                rank = i + 1;
            }
        }

        rank1.setText(String.format("1. %s", rankList.get(0).getName()));
        rank2.setText(String.format("2. %s", rankList.get(1).getName()));
        rank3.setText(String.format("3. %s", rankList.get(2).getName()));
        rank4.setText(String.format("4. %s", rankList.get(3).getName()));
        rank5.setText(String.format("5. %s", rankList.get(4).getName()));
        rank6.setText(String.format("6. %s", rankList.get(5).getName()));
        rank7.setText(String.format("7. %s", rankList.get(6).getName()));
        rank8.setText(String.format("8. %s", rankList.get(7).getName()));
        rank9.setText(String.format("9. %s", rankList.get(8).getName()));
        rank10.setText(String.format("10. %s", rankList.get(9).getName()));
        rankPlayer.setText(String.format("%d. %s", rank, pb.getName()));
    }

    /**
     * Be called if the player ball hit the bound. Detect which bound collides the ball. (X, Y) and 0
     * represents no collision, 1 represents the left one or upper one, and 2 represents the right one
     * or bottom one.
     * 
     * @return the list of boundaries
     */
    private List<Integer> boundWhere() {
        List<Integer> retList = new ArrayList<>();
        retList.add(0);
        retList.add(0);
        if (map.getGraphcs().getX() - (-9500) < pb.getRadius() * 2) {
            retList.set(0, 2);
        } else if (500 - map.getGraphcs().getX() < pb.getRadius() * 2) {
            retList.set(0, 1);
        }
        if (map.getGraphcs().getY() - (-7125) < pb.getRadius() * 2) {
            retList.set(1, 2);
        } else if (375 - map.getGraphcs().getY() < pb.getRadius() * 2) {
            retList.set(1, 1);
        }
        return retList;
    }

    /**
     * Be called if the player ball hit the bound. Detect which bound collides the ball. (X, Y) and 0
     * represents no collision, 1 represents the left one or upper one, and 2 represents the right one
     * or bottom one.
     * 
     * @return the player ball
     */
    public PlayerBall getPb() {
        return pb;
    }

    @Override
    public String toString() {
        return "MainGame [ac=" + ac + ", board=" + board + ", canvas=" + canvas + ", caption=" + caption + ", cc=" + cc
            + ", gameOver=" + gameOver + ", gameRank=" + gameRank + ", isBound=" + isBound + ", isStart=" + isStart
            + ", leaderBoard=" + leaderBoard + ", map=" + map + ", menu=" + menu + ", offsetX=" + offsetX + ", offsetY="
            + offsetY + ", pb=" + pb + ", quit=" + quit + ", rank=" + rank + ", rank1=" + rank1 + ", rank10=" + rank10
            + ", rank2=" + rank2 + ", rank3=" + rank3 + ", rank4=" + rank4 + ", rank5=" + rank5 + ", rank6=" + rank6
            + ", rank7=" + rank7 + ", rank8=" + rank8 + ", rank9=" + rank9 + ", rankList=" + rankList + ", rankPlayer="
            + rankPlayer + ", start=" + start + ", window=" + window + "]";
    }

    public static void main(String[] args) {
        MainGame game = new MainGame();
        game.run();
    }
}
