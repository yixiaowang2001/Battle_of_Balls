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

    public PlayerBall(CanvasWindow canvas, GraphicsGroup gg) {
        cc = new CircleControl(canvas, gg);
        this.canvas = canvas;
        create();
    }

    public void create() {
        Point randPoint = new Point(canvas.getWidth() * 0.5, canvas.getHeight() * 0.5);
        circleShape = new Ellipse(randPoint.getX(), randPoint.getY(), CIRCLE_RAIDUS * 2, CIRCLE_RAIDUS * 2);
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

    public void collisionCircle() {

        // Iterator<Circle> itrCir = cc.getCircleList().iterator(); 
        // while(itrCir.hasNext()) {
        //     Circle cir = itrCir.next();
        //     if (circleShape.getCenter().distance(cir.getPos()) <= CIRCLE_RAIDUS - cir.getR()) {
        //         cc.ifCollision(cir);
        //         System.out.println("P3");
        //         itrCir.remove();
        //     }
        // }

        for(int i = 0; i < cc.getCircleList().size(); i++) {
            Circle cir = cc.getCircleList().get(i);
            if (circleShape.getCenter().distance(cir.getCtr()) <= CIRCLE_RAIDUS - cir.getR()) {
                // cc.ifCollision(cir);
                System.out.println("P3");
                // cc.getCircleList().remove(i);
                // i -= 1;
            }
        }

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

    public double getDiameter() {
        return circleShape.getHeight();
    }
}
