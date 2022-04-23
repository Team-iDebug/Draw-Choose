package com.iDebug.pickloose.canvas;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class NaturalPencil extends Brush {
    NaturalPencil() {
        textureFile = "D:\\softwareDev\\canvas2\\src\\main\\resources\\com\\example\\canvas2\\watercolor.png";
        shape = new Circle(5);
        opacity = 0.2;
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
