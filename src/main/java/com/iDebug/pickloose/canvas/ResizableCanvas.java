package com.iDebug.pickloose.canvas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class ResizableCanvas {
    private final Canvas canvas = new Canvas();
    private double width, height;

    public ResizableCanvas(double width, double height) {
        canvas.setWidth(width);
        canvas.setHeight(height);
        this.width = width;
        this.height = height;
        draw(800,600);
        draw(300,300);
        System.out.println(canvas.getWidth() + " "+ canvas.getHeight());
    }

    public void draw(double width, double height) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image snapshot = canvas.snapshot(null,null);
        gc.clearRect(0, 0, this.width, this.height);

        double prevWidth = canvas.getWidth();
        double prevHeight = canvas.getHeight();
        canvas.setHeight(height);
        canvas.setWidth(width);
        gc.setFill(new ImagePattern(snapshot));
        gc.fillRect(width/2 - prevWidth/2,height/2 - prevHeight/2,prevWidth,prevHeight);

        this.height = height;
        this.width = width;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
