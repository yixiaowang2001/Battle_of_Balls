import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Point;

public class CircleControl {
    
    private static final int LOWER_BOUND = 9500;
    private static final int UPPER_BOUND = 10000;

    private ArrayList<Circle> circleList;
    private CanvasWindow canvas;

    public CircleControl(CanvasWindow canvas) {
        this.canvas = canvas;
        circleList = new ArrayList<>();
        initialize();
    }

    public void initialize() {
        for (int i = 0; i < randInBound(); i++) {
            Circle cir = new Circle(canvas);
            circleList.add(cir);
            cir.addToCanvas();
        }
    }

    public int randInBound() {
        Random rand = new Random();
        return rand.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;
    }

    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("Test", 1000, 500);
        CircleControl cc = new CircleControl(canvas);
    }
}
