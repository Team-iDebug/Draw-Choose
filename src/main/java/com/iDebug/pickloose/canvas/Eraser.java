package com.iDebug.pickloose.canvas;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Eraser extends Brush {
    public Eraser() {
        shape = new Circle(5);
        texture = createTexture(shape,Color.WHITE,1.0);
    }

    @Override
    public void operate() {
        Point2D prevMouseLocation = DrawManager.getInstance().getPrevMouseLocation();
        Point2D mouseLocation = DrawManager.getInstance().getMouseLocation();
        Brush.bresenhamLine(this, prevMouseLocation.getX(), prevMouseLocation.getY(),
                mouseLocation.getX(), mouseLocation.getY());
    }
}
