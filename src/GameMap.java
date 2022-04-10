import edu.macalester.graphics.*;
import java.awt.Color;

public class GameMap {
    private class LineSeg extends Line {

        public LineSeg(double x1, double y1, double x2, double y2) {
            super(x1, y1, x2, y2);
            setStrokeColor(Color.GRAY);
            setStrokeWidth(1);
        }

    }

    public static final int MAP_WIDTH = 10000;
    public static final int MAP_HEIGHT = 5000;
    private GraphicsGroup group;

    public GameMap() {
        group = new GraphicsGroup();

        for (int i = 0; i < MAP_WIDTH; i++) {
            if (i % 50 == 0) {
                LineSeg newLine = new LineSeg(i, 0, i, MAP_HEIGHT);
                group.add(newLine);
            }
        }

        for (int i = 0; i < MAP_HEIGHT; i++) {
            if (i % 50 == 0) {
                LineSeg newLine = new LineSeg(0, i, MAP_WIDTH, i);
                group.add(newLine);
            }
        }
    }

    public GraphicsObject getGraphcs() {
        return group;
    }
}
