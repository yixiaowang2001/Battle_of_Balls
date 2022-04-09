import java.util.List;
import java.awt.Color;
import java.util.HashMap;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;

/**
 * Ball part of the game. Contains the basic logic of ball collision and movement.
 */
public class Ball {
    private static final double BALL_SPEED = 85;
    private static final double BALL_RADIUS = 5;

    private String strOut;
    private int durCounter;
    private Ellipse ballShape;
    private CanvasWindow canvas;
    private GraphicsObject crtObj;
    private List<String> xList, yList;
    private double angle, posX, posY, speedX, speedY, radiusCol;
    private HashMap<String, Point> pointMap = new HashMap<String, Point>();

    /**
     * Create a cannon ball according to input variables.
     * 
     * @param angleIn    the current tank angle
     * @param tankCtr    the current tank center point
     * @param tankH      the tank height
     * @param canvas     the canvas of the ball
     * @param currentMap the current game map
     */
    public Ball(double angleIn, Point tankCtr, double tankH, CanvasWindow canvas, GameMap currentMap) {
        this.canvas = canvas;
        strOut = "PASS";
        durCounter = 0;
        radiusCol = BALL_RADIUS + 0.1;
        angle = angleIn / 360 * 2 * Math.PI;
        posX = tankCtr.getX() + (0.5 * tankH + 10) * Math.cos(angle);
        posY = tankCtr.getY() - (0.5 * tankH + 10) * Math.sin(angle);
        speedX = Math.cos(angle) * BALL_SPEED;
        speedY = -Math.sin(angle) * BALL_SPEED;
        ballShape = new Ellipse(0, 0, BALL_RADIUS * 2, BALL_RADIUS * 2);
        ballShape.setFillColor(Color.BLACK);
        ballShape.setCenter(posX, posY);
        pointMap.put("UP", new Point(posX, posY - radiusCol));
        pointMap.put("UR", new Point(posX + radiusCol / Math.sqrt(2), posY - radiusCol / Math.sqrt(2)));
        pointMap.put("RI", new Point(posX + radiusCol, posY));
        pointMap.put("LR", new Point(posX + radiusCol / Math.sqrt(2), posY + radiusCol / Math.sqrt(2)));
        pointMap.put("LO", new Point(posX, posY + radiusCol));
        pointMap.put("LL", new Point(posX - radiusCol / Math.sqrt(2), posY + radiusCol / Math.sqrt(2)));
        pointMap.put("LE", new Point(posX - radiusCol, posY));
        pointMap.put("UL", new Point(posX - radiusCol / Math.sqrt(2), posY - radiusCol / Math.sqrt(2)));
        xList = List.of("UL", "UR", "RI", "LR", "LL", "LE");
        yList = List.of("UP", "UR", "LR", "LO", "LL", "UL");
    }

    /**
     * Move the ball.
     * 
     * @param dt
     * @param time  duration of the ball
     * @param tank1 in the main game
     * @param tank2 in the main game
     * @return a string of the status of ball collision
     */
    public String ballMove(double dt, double time, Tank tank1, Tank tank2) {
        if (durCounter >= time) {
            durCounter = 0;
            strOut = "TIMEOUT";
        } else {
            ballReflection(tank1, tank2);
            if (!strOut.equals("TANK1") || !strOut.equals("TANK2")) {
                this.posX += speedX * dt;
                this.posY += speedY * dt;
                ballShape.setCenter(posX, posY);
                durCounter += 1;
            }
        }
        return strOut;
    }

    /**
     * Do ball reflection.
     * 
     * @param tank1 in the main game
     * @param tank2 in the main game
     */
    private void ballReflection(Tank tank1, Tank tank2) {
        if (ballShape.getCenter().distance(tank1.getCenter()) <= 20 + 5) {
            strOut = "TANK1";
        } else if (ballShape.getCenter().distance(tank2.getCenter()) <= 20 + 5) {
            strOut = "TANK2";
        } else {
            posX = ballShape.getCenter().getX();
            posY = ballShape.getCenter().getY();
            pointMap.replace("UP", new Point(posX, posY - radiusCol));
            pointMap.replace("UR", new Point(posX + radiusCol / Math.sqrt(2), posY - radiusCol / Math.sqrt(2)));
            pointMap.replace("RI", new Point(posX + radiusCol, posY));
            pointMap.replace("LR", new Point(posX + radiusCol / Math.sqrt(2), posY + radiusCol / Math.sqrt(2)));
            pointMap.replace("LO", new Point(posX, posY + radiusCol));
            pointMap.replace("LL", new Point(posX - radiusCol / Math.sqrt(2), posY + radiusCol / Math.sqrt(2)));
            pointMap.replace("LE", new Point(posX - radiusCol, posY));
            pointMap.replace("UL", new Point(posX - radiusCol / Math.sqrt(2), posY - radiusCol / Math.sqrt(2)));
            pointMap.forEach((k, v) -> {
                crtObj = canvas.getElementAt(v);
                if (crtObj != null && crtObj != ballShape) {
                    if (xList.contains(k)) {
                        speedX = -speedX;
                    }
                    if (yList.contains(k)) {
                        speedY = -speedY;
                    }
                }
            });
        }
    }

    /**
     * Add the ballshape to the canvas.
     */
    public void addToCanvas() {
        canvas.add(ballShape);
    }

    /**
     * Remove the ballshape from the canvas.
     */
    public void removeFromCanvas() {
        canvas.remove(ballShape);
    }

    @Override
    public String toString() {
        return "Ball [angle=" + angle + ", ballShape=" + ballShape + ", canvas=" + canvas + ", crtObj=" + crtObj
            + ", durCounter=" + durCounter + ", pointMap=" + pointMap + ", posX=" + posX + ", posY=" + posY
            + ", radiusCol=" + radiusCol + ", speedX=" + speedX + ", speedY=" + speedY + ", strOut=" + strOut
            + ", xList=" + xList + ", yList=" + yList + "]";
    }
}
