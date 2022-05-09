package com.iDebug.pickloose.canvas;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class OilBrush extends Brush {
    public OilBrush() {
        textureFile = "file:../src/main/resources/com/iDebug/pickloose/textures/brush.png";
        shape = new Circle(5);
        opacity = 0.2;
        color = DrawManager.getInstance().getSelectedColor();
        texture = createTexture(shape,color,opacity);
    }

    @Override
    public void operate() {
        Point2D prevMouseLocation = DrawManager.getInstance().getPrevMouseLocation();
        Point2D mouseLocation = DrawManager.getInstance().getMouseLocation();
        Color currColor = DrawManager.getInstance().getSelectedColor();
        if(!currColor.equals(color)) {
            texture = createTexture(shape,currColor,opacity);
            color = currColor;
        }
        Brush.bresenhamLine(this, prevMouseLocation.getX(), prevMouseLocation.getY(),
                mouseLocation.getX(), mouseLocation.getY());
    }
}
