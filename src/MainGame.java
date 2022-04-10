import java.util.List;
import java.util.Random;
import java.awt.Color;

import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

public class MainGame {
    
    private CanvasWindow canvas;
    // canvas
    // hex & ball collision
    // UserInterface
    
    public MainGame() {
        canvas = new CanvasWindow("Test", 2000, 1000);
    }

    public static void main(String[] args) {
        MainGame game = new MainGame();
    }
}
