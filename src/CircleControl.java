import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;

/**
 * Initialize the circles and create the list to store them.
 */
public class CircleControl {
    private static final int LOWER_BOUND = 4500;
    private static final int UPPER_BOUND = 5000;

    private List<Circle> circleList;
    private CanvasWindow canvas;

    /**
     * the constructor of the circlecontrol
     * 
     * @param canvas the canvas that displays the circle
     */
    public CircleControl(CanvasWindow canvas) {
        this.canvas = canvas;
        circleList = new ArrayList<>();

    }

    /**
     * Create a new circle and add it to the list.
     */
    public void initialize() {
        for (int i = 0; i < 5000; i++) {
            Circle cir = new Circle(canvas);
            canvas.add(cir.getGraphics());
            circleList.add(cir);
        }
    }

    /**
     * Controls the number of the circles.
     * 
     * @param offsetX,offsetY prevent the circles from moving out of the boundry
     */
    public void controlNum(double offsetX, double offsetY) {
        if (circleList.size() < LOWER_BOUND) {
            for (int i = 0; i < randInBound() - circleList.size(); i++) {
                Circle cir = new Circle(canvas);
                cir.getGraphics().moveBy(offsetX, offsetY);
                canvas.add(cir.getGraphics());
                circleList.add(cir);
            }
        }
    }

    /**
     * move the circle
     */
    public void moveCir(double dx, double dy) {
        Iterator<Circle> itrCir = circleList.iterator();
        while (itrCir.hasNext()) {
            Circle cir = itrCir.next();
            cir.getGraphics().moveBy(dx, dy);
        }
    }

    /**
     * get a random int
     * 
     * @return a random int
     */
    private int randInBound() {
        Random rand = new Random();
        return rand.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;
    }

    /**
     * get the list of the circles
     * 
     * @return the list of the circles
     */
    public List<Circle> getCircleList() {
        return circleList;
    }

    @Override
    public String toString() {
        return "CircleControl [canvas=" + canvas + ", circleList=" + circleList + "]";
    }
}
