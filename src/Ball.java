import java.awt.Color;
import java.util.PriorityQueue;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;

/**
 * The ball's abstract class. AIBall and PlayerBall use this.
 */
public abstract class Ball {
    double speed;
    String name;
    CanvasWindow canvas;

    /**
     * The constructor of the ball
     */
    public Ball(CanvasWindow canvas) {
        this.canvas = canvas;
        name = "";
        speed = 0;
        create();
    }

    /**
     * Creates a random color
     * 
     * @return a random color
     *
     */
    Color createRandColor() {
        Random rand = new Random();
        float[] hsb = Color.RGBtoHSB(rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0,
            null);
        Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        return color;
    }

    /**
     * Check for collision between the objects
     * 
     * @return the status
     *
     */
    boolean isCollision(Point ctr1, Point ctr2, double r1, double r2, double rate) {
        double dis = ctr1.distance(ctr2);
        PriorityQueue<Double> pq = new PriorityQueue<>();
        pq.add(r1);
        pq.add(r2);
        double r = pq.poll();
        double R = pq.poll();
        if (r / R <= rate) {
            if (dis <= R) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create the ball
     */
    abstract void create();

    /**
     * Resize the ball when it hit circles
     */
    abstract void resizeCir();

    /**
     * Get the ball's radius
     * 
     * @return the ball's radius
     */
    abstract public double getRadius();

    /**
     * Get the ball's name
     * 
     * @return the ball's name
     */
    abstract public String getName();

    /**
     * Get the ball's shape
     * 
     * @return the ball's shape
     */
    abstract public GraphicsObject getGraphics();

    @Override
    public String toString() {
        return "Ball [canvas=" + canvas + ", name=" + name + ", speed=" + speed + "]";
    }
}
