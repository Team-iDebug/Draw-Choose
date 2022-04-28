package com.iDebug.pickloose.canvas;

import com.iDebug.pickloose.NetworkManager;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class DrawManager {
    private static DrawManager drawManager;
    private Point2D mouseLocation;
    private Point2D prevMouseLocation;
    private Tool selectedTool;
    private Color selectedColor;
    private ResizableCanvas canvas;
    private GraphicsContext graphicsContext;

    private DrawManager() {
        selectedTool = new OilBrush();
        mouseLocation = new Point2D(0,0);
        prevMouseLocation = new Point2D(0,0);
    }

    public static DrawManager getInstance() {
        if(drawManager == null)
            drawManager = new DrawManager();
        return drawManager;
    }

    public Point2D getMouseLocation() {
        return mouseLocation;
    }

    public void updateCanvas(Image image) {
        graphicsContext.drawImage(image,0,0);
    }

    public void updateAndOperate(double x, double y) {
        updateMouseLocation(x,y);
        canvas.updateSnap();
        NetworkManager.getInstance().sendStream(x + " " + y);
        selectedTool.operate();
    }

    public void updateMouseLocation(double x, double y) {
        this.prevMouseLocation = mouseLocation;
        this.mouseLocation = new Point2D(x,y);
    }

    public Tool getSelectedTool() {
        return selectedTool;
    }

    public void setSelectedTool(Tool selectedTool) {
        this.selectedTool = selectedTool;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(ResizableCanvas canvas) {
        this.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public Point2D getPrevMouseLocation() {
        return prevMouseLocation;
    }

    public void setMouseLocation(double x, double y) {
        mouseLocation = new Point2D(x,y);
    }

    public void copyCanvas() {
        System.out.println("copied!!!");
        canvas.copy();
    }

    public void pasteCanvas() {
        canvas.paste();
    }

    public void undo() {
        canvas.undo();
    }

    public void redo() {
        canvas.redo();
    }

    public void listenMouseEvent(MouseEvent e) {
        selectedTool.listenMouseEvent(e);
    }
}
