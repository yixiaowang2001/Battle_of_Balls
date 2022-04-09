import edu.macalester.graphics.*;

/**
 * A rectangle map with walls
 */
public interface GameMap {

    /**
     * This map's visual interface, which you can add to a CanvasWindow or GraphicsGroup.
     */
    GraphicsObject getGraphics();
}
