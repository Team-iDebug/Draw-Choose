package com.iDebug.pickloose.canvas;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class OilBrush extends Brush {

    public OilBrush() {
        textureFile = "D:\\softwareDev\\iDebug\\src\\main\\resources\\com\\iDebug\\pickloose\\textures\\brush.png";
        shape = new Circle(5);
        opacity = 0.6;
        texture = createTexture(this, Color.BLACK, Color.GREEN);
    }

    @Override
    public void operate() {
        Point2D prevMouseLocation = DrawManager.getInstance().getPrevMouseLocation();
        Point2D mouseLocation = DrawManager.getInstance().getMouseLocation();
        Brush.bresenhamLine(this, prevMouseLocation.getX(), prevMouseLocation.getY(),
                mouseLocation.getX(), mouseLocation.getY());
    }
}
