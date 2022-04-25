import java.util.HashMap;
import java.util.List;
import java.util.Random;

import java.util.Iterator;

import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

public class PlayerBall {

    private static final int CIRCLE_RAIDUS = 20;
    private CanvasWindow canvas;
    private Ellipse circleShape;
    private CircleControl cc;
    private AIBall aiBall;
    private Boolean flag;

    public PlayerBall(CanvasWindow canvas) {
        cc = new CircleControl(canvas);
        this.canvas = canvas;
        create();
    }

    public void create() {
        Point startPoint = new Point(canvas.getWidth() * 0.5 - CIRCLE_RAIDUS, canvas.getHeight() * 0.5 - CIRCLE_RAIDUS);
        circleShape = new Ellipse(startPoint.getX(), startPoint.getY(), CIRCLE_RAIDUS * 2, CIRCLE_RAIDUS * 2);
        circleShape.setFillColor(createRandColor());
        circleShape.setStroked(false);
        canvas.add(circleShape);
    }

    public Color createRandColor() {
        Random rand = new Random();
        float[] hsb = Color.RGBtoHSB(rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0,
                null);
        Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        return color;
    }

    public void move() {
        // move the ball in the canvas

    }

    public void resize() {
    //resize the ball in the canvas
    if (flag) {
        circleShape.setSize(circleShape.getWidth() + 10, circleShape.getHeight() + 10);
        flag =  false;
    }
    }

    public void collision() {
        // check for collision with other balls

    }

    public void collisionCircle(double dx, double dy) {

        Iterator<Circle> itrCir = cc.getCircleList().iterator(); 
        while(itrCir.hasNext()) {
            Circle cir = itrCir.next();
            cir.getShape().moveBy(dx, dy);
            if (circleShape.getCenter().distance(cir.getCtr()) <= CIRCLE_RAIDUS - cir.getR()) {
                cc.ifCollision(cir);
                System.out.println("P3");
                itrCir.remove();
            }
        }
        cc.controlNum();
    }

    private void resizeCir() {
        // TODO
        
    }

    public void collisionBall() {
        // check for collision with other balls
        for (Point point : aiBall.aiBallPoint) {
            if (Math.sqrt(Math.pow(point.getX() - circleShape.getX(), 2)
                    + Math.pow(point.getY() - circleShape.getY(), 2)) <= (getDiameter() / 2
                            + aiBall.getDiameter() / 2)) {
                flag = true;
            }
        }
    }

    public CircleControl returnCC() {
        return cc;
    }

    public double getDiameter() {
        return circleShape.getHeight();
    }
}
