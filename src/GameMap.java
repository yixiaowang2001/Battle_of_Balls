import java.awt.Color;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Line;

/**
 * The background of the game.
 */
public class GameMap {
    private class LineSeg extends Line {

        /**
         * The constructor of the line segment
         * 
         * @param x1 initial x
         * @param y1 initial x
         * @param x2 end x
         * @param y2 end y
         */
        public LineSeg(double x1, double y1, double x2, double y2) {
            super(x1, y1, x2, y2);
            float[] hsb = Color.RGBtoHSB(212, 211, 211, null);
            Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
            setStrokeColor(color);
            setStrokeWidth(1);
        }
    }

    static final int MAP_WIDTH = 10 * MainGame.CANVAS_WIDTH;
    static final int MAP_HEIGHT = 10 * MainGame.CANVAS_HEIGHT;
    private GraphicsGroup group;

    /**
     * The constructor of the game map
     */
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

    /**
     * get the group of the map
     * 
     * @return the group of the map
     */
    public GraphicsObject getGraphcs() {
        return group;
    }

    @Override
    public String toString() {
        return "GameMap [group=" + group + "]";
    }
}
