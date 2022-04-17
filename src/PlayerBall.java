import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.Point;

public class PlayerBall {
    
    private static final int CIRCLE_RAIDUS = 10;
    private CanvasWindow canvas;
    private double posX, posY;
    private Ellipse circleShape;
    private CircleControl cc;

    public PlayerBall(CanvasWindow canvas) {
        cc = new CircleControl(canvas);
        this.canvas = canvas;
        posX = 0;
        posY = 0;
        create();
    }

    public void create() {
        Point randPoint = createRandPos();
        circleShape = new Ellipse(randPoint.getX(), randPoint.getY(), CIRCLE_RAIDUS, CIRCLE_RAIDUS);
        circleShape.setFillColor(createRandColor());
        circleShape.setStroked(false);
        canvas.add(circleShape);
    }

    private Point createRandPos() {
        Random rand = new Random();
        double minX = -4.5 * canvas.getWidth() + CIRCLE_RAIDUS;
        double maxX = 5.5 * canvas.getWidth() - CIRCLE_RAIDUS;
        double minY = -4.5 * canvas.getHeight() + CIRCLE_RAIDUS;
        double maxY = 5.5 * canvas.getWidth() - CIRCLE_RAIDUS;
        double randomX = minX + (maxX - minX) * rand.nextDouble();
        double randomY = minY + (maxY - minY) * rand.nextDouble();
        return new Point(randomX, randomY);
    }

    public Color createRandColor() {
        Random rand = new Random();
        float[] hsb = Color.RGBtoHSB(rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0, null);
        Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        return color;
    }


    public void move() {
    //move the ball in the canvas
    
    }

    public void resize() {
    //resize the ball in the canvas

    }

    public void collision() {
    //check for collision with other balls
    
    }

    public void collisionCircle(List<Circle> circleList) {
        for (Circle cir : circleList) {
            if (new Point(posX, posY).distance(cir.getPos()) <= CIRCLE_RAIDUS - cir.getR()) {
                cc.ifCollision(cir);
            }
        }
    }
}
