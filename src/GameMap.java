import edu.macalester.graphics.*;
import java.awt.Color;

public class GameMap {
    private class LineSeg extends Line {

        public LineSeg(double x1, double y1, double x2, double y2) {
            super(x1, y1, x2, y2);
            float[] hsb = Color.RGBtoHSB(212, 211, 211, null);
            Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
            setStrokeColor(color);
            setStrokeWidth(1);
        }
    }

    public static final int MAP_WIDTH = 10 * MainGame.CANVAS_WIDTH;
    public static final int MAP_HEIGHT = 10 * MainGame.CANVAS_HEIGHT;
    private GraphicsGroup group;

    public GameMap() {
        group = new GraphicsGroup();

        for (int i = 0; i <= MAP_WIDTH; i++) {
            if (i % 50 == 0) {
                LineSeg newLine = new LineSeg(i, 0, i, MAP_HEIGHT);
                group.add(newLine);
            }
        }

        for (int i = 0; i <= MAP_HEIGHT; i++) {
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
