import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Point;

/**
 * Create AI ball
 */
public class AIBall extends Ball {
    private Ellipse ballShape;
    private GraphicsText nameText;
    private double radius, randCos, randSin, nextX, nextY;
    private int moveCount;

    /**
     * The ball that is controlled by the program
     */
    public AIBall(CanvasWindow canvas) {
        super(canvas);
        radius = createRandRadius();
        moveCount = 0;
        randCos = 0;
        randSin = 0;
        nextX = Double.MAX_VALUE;
        nextY = Double.MAX_VALUE;
        create();
    }

    /**
     * The automatically AIball moving method
     * 
     * @param offsetX,offsetY prevent the balls from moving out of the boundry
     */
    public void autoMove(double offsetX, double offsetY) {
        updateSpeed();
        Random rand = new Random();
        double ballX = ballShape.getCenter().getX();
        double ballY = ballShape.getCenter().getY();
        if (moveCount == 0) {
            randCos = -1 + 2 * rand.nextDouble();
            randSin = -1 + 2 * rand.nextDouble();
            nextX = ballX - randCos * speed;
            nextY = ballY - randSin * speed;
            if (hitBound(offsetX, offsetY, 20, nextX, nextY).get(0)) {
                randCos = -randCos;
            }
            if (hitBound(offsetX, offsetY, 20, nextX, nextY).get(1)) {
                randSin = -randSin;
            }
            ballShape.moveBy(-randCos * speed, -randSin * speed);
            nameText.moveBy(-randCos * speed, -randSin * speed);
            moveCount = rand.nextInt(300) + 300;
        } else {
            nextX = ballX - randCos * speed;
            nextY = ballY - randSin * speed;
            if (hitBound(offsetX, offsetY, 20, nextX, nextY).get(0)) {
                randCos = -randCos;
            }
            if (hitBound(offsetX, offsetY, 20, nextX, nextY).get(1)) {
                randSin = -randSin;
            }
            ballShape.moveBy(-randCos * speed, -randSin * speed);
            nameText.moveBy(-randCos * speed, -randSin * speed);
            moveCount--;
        }
    }

    /**
     * Detect which bound collides the AI ball. (X, Y) and true means there is a collision.
     * 
     * @param offsetX,offsetY prevent the balls from moving out of the boundry
     * @param margin          the margin of the boundry
     * @param nextX,nextY     the next position of the AI ball
     * @return returnList the status of the boundry
     */
    private List<Boolean> hitBound(double offsetX, double offsetY, double margin, double nextX, double nextY) {
        List<Boolean> returnList = new ArrayList<>();
        returnList.add(false);
        returnList.add(false);
        double leftX = canvas.getCenter().getX() + offsetX - canvas.getWidth() * 5 - margin + radius;
        double rightX = canvas.getCenter().getX() + offsetX + canvas.getWidth() * 5 + margin - radius;
        double upY = canvas.getCenter().getY() + offsetY - canvas.getHeight() * 5 - margin + radius;
        double lowY = canvas.getCenter().getY() + offsetY + canvas.getHeight() * 5 + margin - radius;
        if (nextX >= rightX || nextX <= leftX) {
            returnList.set(0, true);
        }
        if (nextY >= lowY || nextY <= upY) {
            returnList.set(1, true);
        }
        return returnList;
    }

    /**
     * updates the ball's speed
     */
    private void updateSpeed() {
        speed = 100 * 1 / (getRadius() * 2) + 1.2;
    }

    private Point createRandPos() {
        Random rand = new Random();
        double minX = -4.5 * canvas.getWidth() + radius;
        double maxX = 5.5 * canvas.getWidth() - radius;
        double minY = -4.5 * canvas.getHeight() + radius;
        double maxY = 5.5 * canvas.getWidth() - radius;
        double randomX = minX + (maxX - minX) * rand.nextDouble();
        double randomY = minY + (maxY - minY) * rand.nextDouble();
        return new Point(randomX, randomY);
    }

    private double createRandRadius() {
        Random rand = new Random();
        double randR = 100 * (rand.nextDouble()) + 5 * Circle.CIRCLE_RAIDUS;
        return randR;
    }

    private String createRandName() {
        StringBuilder sb = new StringBuilder();
        List<String> firstList = List.of("Joey", "Whit", "Evan", "Donny", "Foster", "Olia", "Grady", "Bella",
            "Darin", "Mickey", "Hank", "Kim", "Peter", "Jeremy", "Jess", "Ezri",
            "Vern", "Fran", "Romeo", "Chris", "Dale", "Beau", "Cliff", "Hamel", "Garv", "Brain");
        List<String> lastList = List.of("Mayr", "Grace", "Sand", "Wood", "Dutra", "Flury");
        Random rand = new Random();
        int firstIndex = rand.nextInt(firstList.size());
        int lastIndex = rand.nextInt(lastList.size());
        sb.append(firstList.get(firstIndex));
        sb.append(" ");
        sb.append(lastList.get(lastIndex));
        return sb.toString();
    }

    /**
     * Deals with the collision of the AI ball with the other balls
     * 
     * @param ac the list of the AI balls
     */
    public void collisionAiBall(AIBallControl ac, List<Ball> rankList) {
        Iterator<AIBall> itrBall = ac.getBallQueue().iterator();
        while (itrBall.hasNext()) {
            AIBall ball = itrBall.next();
            if (isCollision(ballShape.getCenter(), ball.getCtr(), getRadius(), ball.getRadius(), 0.85)) {
                if (getRadius() > ball.getRadius()) {
                    resizeBall(ball);
                    rankList.remove(ball);
                    canvas.remove(ball.getGraphics());
                    canvas.remove(ball.getGraphicsName());
                    itrBall.remove();
                }
            }
        }
    }

    /**
     * Deals with the collision of the AI ball with the circles
     * 
     * @param cc the list of the circles
     */
    public void collisionCircle(CircleControl cc) {
        Iterator<Circle> itrCir = cc.getCircleList().iterator();
        while (itrCir.hasNext()) {
            Circle cir = itrCir.next();
            if (ballShape.getCenter().distance(cir.getCtr()) <= ballShape.getHeight() / 2 + cir.getRadius()) {
                resizeCir();
                canvas.remove(cir.getGraphics());
                itrCir.remove();
            }
        }
    }

    /**
     * resize the AI ball
     * 
     * @param otherBall the ball that is eaten
     */
    private void resizeBall(Ball otherBall) {
        ballShape.setSize(Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(otherBall.getRadius(), 2)),
            Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(otherBall.getRadius(), 2)));
        nameText.setFontSize(radius * 0.4);
        nameText.setCenter(ballShape.getCenter());
    }

    /**
     * get the circle of the ball
     * 
     * @return the point of the center
     */
    public Point getCtr() {
        return ballShape.getCenter();
    }

    /**
     * get the name of the ball
     * 
     * @return the name of the ball
     */
    public GraphicsText getGraphicsName() {
        return nameText;
    }

    @Override
    void create() {
        name = createRandName();
        Color color = createRandColor();
        Point randPoint = createRandPos();
        ballShape = new Ellipse(randPoint.getX(), randPoint.getY(), radius, radius);
        ballShape.setFillColor(color);
        ballShape.setStrokeColor(color.darker());
        ballShape.setStrokeWidth(5);
        nameText = new GraphicsText(name);
        nameText.setFontSize(radius * 0.4);
        nameText.setCenter(ballShape.getCenter());
    }

    @Override
    void resizeCir() {
        ballShape.setSize(Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(Circle.CIRCLE_RAIDUS * 2, 2)),
            Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(Circle.CIRCLE_RAIDUS * 2, 2)));
        nameText.setFontSize(radius * 0.4);
        nameText.setCenter(ballShape.getCenter());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getRadius() {
        return ballShape.getHeight() / 2;
    }

    @Override
    public GraphicsObject getGraphics() {
        return ballShape;
    }

    @Override
    public String toString() {
        return "AIBall [ballShape=" + ballShape + ", moveCount=" + moveCount + ", nameText=" + nameText + ", nextX="
            + nextX + ", nextY=" + nextY + ", radius=" + radius + ", randCos=" + randCos + ", randSin=" + randSin + "]";
    }
}
