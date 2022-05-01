import java.util.List;
import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Point;
import java.awt.Color;

public class AIBall implements Ball {
    private Ellipse ballShape;
    private GraphicsText nameText;
    private CanvasWindow canvas;
    private double radius, moveSpeed, randCos, randSin, moveX, moveY, offsetX, offsetY;
    private int moveCount;
    private Color color;
    private String name;
    private List<Ball> rankList;

    public AIBall(CanvasWindow canvas, List<Ball> rankList) {
        this.canvas = canvas;
        this.rankList = rankList;
        radius = createRandRadius();
        Point randPoint = createRandPos();
        color = createRandColor();
        name = createRandName();
        moveSpeed = 0;
        moveCount = 0;
        randCos = 0;
        randSin = 0;
        offsetX = 0;
        offsetY = 0;
        // nextX = Double.MAX_VALUE;
        // nextY = Double.MAX_VALUE;

        ballShape = new Ellipse(randPoint.getX(), randPoint.getY(), radius, radius);
        ballShape.setFillColor(color);
        ballShape.setStrokeColor(color.darker());
        ballShape.setStrokeWidth(5);

        nameText = new GraphicsText(name);

        nameText.setFontSize(radius * 0.3);
        nameText.setCenter(ballShape.getCenter());

    }

    public void autoMove(double CANVAS_WIDTH, double CANVAS_HEIGHT) {
        updateSpeed();
        Random rand = new Random();
        double ballX = ballShape.getCenter().getX();
        double ballY = ballShape.getCenter().getY();
        boolean bound = ballShape.getCenter().getX() < -10 * CANVAS_WIDTH + getRadius() ||
        ballShape.getCenter().getX() > 10 * CANVAS_WIDTH - getRadius() ||
        ballShape.getCenter().getY() < -10 * CANVAS_HEIGHT + getRadius()||
        ballShape.getCenter().getY() > 10 * CANVAS_HEIGHT - getRadius();
        if (moveCount == 0 && !bound) {
            randCos = -1 + 2 * rand.nextDouble();
            randSin = -1 + 2 * rand.nextDouble();
            moveX =  - randCos * moveSpeed;
            moveY =  - randSin * moveSpeed;
            ballShape.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
            nameText.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
            offsetX += moveX;
            offsetY += moveY;
            moveCount = rand.nextInt(300) + 300;
        } else if (bound) {
            System.out.println("out of bound");
            // ballShape.moveBy(-moveX,-moveY);
            // nameText.moveBy(-moveX,-moveY);
            //碰到之后要怎么动，或者重置它的位置
            offsetX = 0;
            offsetY = 0;
            moveCount = 0;
        } else {
            ballShape.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
            nameText.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
            offsetX += moveX;
            offsetY += moveY;
            moveCount--;
        }
    }

    // public void autoMove(double offsetX, double offsetY) {
    // updateSpeed();
    // Random rand = new Random();
    // double ballX = ballShape.getCenter().getX();
    // double ballY = ballShape.getCenter().getY();
    // if (moveCount == 0) {
    // while (!testBound(offsetX, offsetY, 50, nextX, nextY)) {
    // System.out.println("你在写锤子");
    // randCos = -1 + 2 * rand.nextDouble();
    // randSin = -1 + 2 * rand.nextDouble();
    // nextX = ballX - randCos * moveSpeed;
    // nextY = ballY - randSin * moveSpeed;
    // }
    // ballShape.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
    // nameText.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
    // moveCount = 500;
    // } else {
    // while (!testBound(offsetX, offsetY, 0, nextX, nextY)) {
    // randCos = -1 + 2 * rand.nextDouble();
    // randSin = -1 + 2 * rand.nextDouble();
    // nextX = ballX - randCos * moveSpeed;
    // nextY = ballY - randSin * moveSpeed;
    // }
    // ballShape.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
    // nameText.moveBy(-randCos * moveSpeed, -randSin * moveSpeed);
    // moveCount--;
    // }
    // }

    private boolean testBound(double offsetX, double offsetY, double margin, double nextX, double nextY) {
        double leftX = canvas.getCenter().getX() + offsetX - canvas.getWidth() * 10 / 2 + margin - radius;
        double rightX = canvas.getCenter().getX() + offsetX + canvas.getWidth() * 10 / 2 - margin + radius;
        double upY = canvas.getCenter().getY() + offsetY - canvas.getHeight() * 10 / 2 + margin - radius;
        double lowY = canvas.getCenter().getY() + offsetY + canvas.getHeight() * 10 / 2 - margin + radius;
        return (nextX > rightX && nextX < leftX) && (nextY > lowY && nextY < upY) ? true : false;
    }

    private void updateSpeed() {
        moveSpeed = 100 * 1 / (getRadius() * 2) + 1.2;
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

    private Color createRandColor() {
        Random rand = new Random();
        float[] hsb = Color.RGBtoHSB(rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0,
                null);
        Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        return color;
    }

    private double createRandRadius() {
        Random rand = new Random();
        double randR = 100 * (rand.nextDouble()) + 5 * Circle.CIRCLE_RAIDUS;
        return randR;
    }

    private String createRandName() {
        StringBuilder sb = new StringBuilder();
        List<String> namelist = List.of("Joey", "Aurelio", "Evan", "Donny", "Foster", "Dwayne", "Grady", "Quinton", "Darin", "Mickey", "Hank", "Kim", "Peter", "Jeremy", "Jess", "Jimmie",
                "Vern", "Pasquale", "Romeo", "Chris", "Dale", "Beau", "Cliff", "Timothy", "Raphael", "Brain");
        List<String> adjlist = List.of("Compassionate", "Friendly", "Reliable", "Conscientious", "Funny", "Reserved");
        Random rand = new Random();
        int nameIndex = rand.nextInt(namelist.size());
        int adjIndex = rand.nextInt(adjlist.size());
        sb.append(adjlist.get(adjIndex));
        sb.append(" ");
        sb.append(namelist.get(nameIndex));
        return sb.toString();
    }

    public void collisionAiBall(AIBallControl ac) {
        Iterator<AIBall> itrBall = ac.getBallQueue().iterator();
        while (itrBall.hasNext()) {
            AIBall ball = itrBall.next();
            if (isCollision(ballShape.getCenter(), ball.getCtr(), getRadius(), ball.getRadius(), 0.85)) {
                if (getRadius() > ball.getRadius()) {
                    resizeBall(ball.getBall());
                    rankList.remove(ball);
                    canvas.remove(ball.getGraphics());
                    canvas.remove(ball.getGraphicsName());
                    itrBall.remove();
                }
            }
        }
    }

    public void collisionCircle(CircleControl cc) {
        Iterator<Circle> itrCir = cc.getCircleList().iterator();
        while (itrCir.hasNext()) {
            Circle cir = itrCir.next();
            if (ballShape.getCenter().distance(cir.getCtr()) <= ballShape.getHeight() / 2 + cir.getRadius()) {
                // resizeCir();
                canvas.remove(cir.getGraphics());
                itrCir.remove();
            }
        }
    }

    private boolean isCollision(Point ctr1, Point ctr2, double r1, double r2, double rate) {
        double dis = ctr1.distance(ctr2);
        PriorityQueue<Double> pq = new PriorityQueue<>();
        pq.add(r1);
        pq.add(r2);
        double r = pq.poll();
        double R = pq.poll();
        if (r / R <= rate) {
            if (dis <= R + r) {
                return true;
            }
        }
        return false;
    }

    private void resizeBall(Ellipse otherBall) {
        double resizeRate = 1;

        ballShape.setSize(ballShape.getWidth() + otherBall.getHeight() / 2 * resizeRate,
                ballShape.getWidth() + otherBall.getHeight() / 2 * resizeRate);
        nameText.setFontSize(radius * 0.4);
        nameText.setCenter(ballShape.getCenter());
    }

    private Ellipse getBall() {
        return ballShape;
    }

    public double getRadius() {
        return ballShape.getHeight() / 2;
    }

    public GraphicsObject getGraphics() {
        return ballShape;
    }

    public Point getCtr() {
        return ballShape.getCenter();
    }

    public String getName() {
        return name;
    }

    public GraphicsText getGraphicsName() {
        return nameText;
    }
}
