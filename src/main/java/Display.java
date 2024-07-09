package main.java;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

public class Display extends Canvas {
    
    GraphicsContext gc;
    final int HEIGHT =64 , WIDTH=32 , scale = 12;
    int[][] displayscreen = new int[HEIGHT][WIDTH];
    //Color On=Color.TURQUOISE , Off=Color.LAVENDERBLUSH;  earlier defaults
    Color On = Color.valueOf("#00FFFF") , Off = Color.valueOf("#000080");

    public Display() {
        super(800, 400);
        setFocusTraversable(true);

        gc = this.getGraphicsContext2D();
    }

    public void render() {
        for(int x = 0; x < displayscreen.length; x++) {
            for(int y = 0; y < displayscreen[y].length; y++) {
                if (displayscreen[x][y]==1) {
                    gc.setFill(On);  //Color.TURQUOISE
                } else {
                    gc.setFill(Off); //Color.LAVENDERBLUSH
                }

                gc.fillRect(x*scale, (y*scale), scale, scale);
            }
        }
    }

}
