
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;

public class CircleControl {

    private static final int LOWER_BOUND = 9500;
    private static final int UPPER_BOUND = 10000;

    private List<Circle> circleList;
    private CanvasWindow canvas;

    public CircleControl(CanvasWindow canvas) {
        this.canvas = canvas;
        circleList = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < randInBound(); i++) {
            Circle cir = new Circle(canvas);
            canvas.add(cir.getShape());
            circleList.add(cir);
        }
    }

    public void controlNum(double offsetX, double offsetY) {
        if (circleList.size() < LOWER_BOUND) {
            System.out.println("Control Num");
            for (int i = 0; i < randInBound() - circleList.size(); i++) {
                Circle cir = new Circle(canvas);
                cir.getShape().moveBy(offsetX, offsetY);
                canvas.add(cir.getShape());
                circleList.add(cir);
            }
        }
    }

    private int randInBound() {
        Random rand = new Random();
        return rand.nextInt(UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;
    }

    public List<Circle> getCircleList() {
        return circleList;
    }
}
