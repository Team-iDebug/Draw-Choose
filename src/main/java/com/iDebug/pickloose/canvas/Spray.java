package com.iDebug.pickloose.canvas;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Spray extends Tool {
    public Spray() {

    }

    @Override
    public void operate() {
        Point2D location = DrawManager.getInstance().getMouseLocation();
        double radius = 5;
        double x = location.getX();
        double y = location.getY();
        double width = DrawManager.getInstance().getCanvas().getWidth();
        double height = DrawManager.getInstance().getCanvas().getHeight();
        double minX = Math.max(0,x-radius);
        double maxX = Math.min(width,x+radius);
        double minY = Math.max(0,y-radius);
        double maxY = Math.min(height,y+radius);
        GraphicsContext gc = DrawManager.getInstance().getGraphicsContext();
        PixelWriter pixelWriter = gc.getPixelWriter();
        for(int i = 0 ; i < 30 ; i++) {
            int currX = (int) Math.floor(Math.random()*(maxX-minX+1)+minX);
            int currY = (int) Math.floor(Math.random()*(maxY-minY+1)+minY);
            Color selectedColor = DrawManager.getInstance().getSelectedColor();
            pixelWriter.setColor(currX,currY,selectedColor);
        }
    }

    @Override
    public void listenEvent(int x, int y, String event) {
        if (event.equals("MOUSE_CLICKED") || event.equals("MOUSE_DRAGGED")) {
            DrawManager.getInstance().updateAndOperate(x,y,event);
        }
    }

    @Override
    public void listenMouseEvent(MouseEvent e) {
        if (e.getEventType().equals(MouseEvent.MOUSE_CLICKED) || e.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
            DrawManager.getInstance().updateAndOperate(e.getX(),e.getY(),e.getEventType());
        }
    }
}
