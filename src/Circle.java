import edu.macalester.graphics.*;

public class Circle {
    
    private CanvasWindow canvas;

    private static final double CIRCLE_RAIDUS = 10;
    private Ellipse circleShape;
    private double posX, posY;

    public Circle(CanvasWindow canvas) {
        this.canvas = canvas;

    }

    public void create() {
    // create a circle
    
    }

    public double getX() {
        return posX;
    }

    public double getY() {
        return posY;
    }


    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("Test", 2000, 1000);
    }
}
