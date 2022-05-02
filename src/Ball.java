import java.util.PriorityQueue;
import java.util.Random;
import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;

public abstract class Ball {

    double speed;
    String name;
    CanvasWindow canvas;

    public Ball(CanvasWindow canvas) {
        this.canvas = canvas;
        name = "";
        speed = 0;
        create();
    }

    Color createRandColor() {
        Random rand = new Random();
        float[] hsb = Color.RGBtoHSB(rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0,
                null);
        Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        return color;
    }

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

    abstract protected void create();
    abstract protected void resizeCir();
    abstract public double getRadius();
    abstract public String getName();
    abstract public GraphicsObject getGraphics();
}
