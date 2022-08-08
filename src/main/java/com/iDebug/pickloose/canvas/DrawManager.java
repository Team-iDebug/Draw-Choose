package com.iDebug.pickloose.canvas;

import com.iDebug.pickloose.NetworkManager;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
        selectedColor = Color.rgb(26,77,77);
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

    public void updateAndOperate(int x, int y, String event) {
        updateMouseLocation(x,y,event);
        selectedTool.operate();
        canvas.updateSnap();
    }

    public void updateAndOperate(double x, double y,EventType event) {
        updateMouseLocation(x,y,event);
        selectedTool.operate();
        canvas.updateSnap();
        NetworkManager.getInstance().sendToolAction(String.valueOf(x),String.valueOf(y),event.getName());
    }

    public void updateMouseLocation(int x, int y, String event) {
        this.prevMouseLocation = mouseLocation;
        this.mouseLocation = new Point2D(x,y);
    }

    public void updateMouseLocation(double x, double y,EventType event) {
        this.prevMouseLocation = mouseLocation;
        this.mouseLocation = new Point2D(x,y);
        NetworkManager.getInstance().sendToolAction(String.valueOf(x),String.valueOf(y),event.getName());
    }

    public Tool getSelectedTool() {
        return selectedTool;
    }

    public void setSelectedTool(Tool selectedTool) {
        this.selectedTool = selectedTool;
    }

    public ResizableCanvas getCanvas() {
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

    public void clearCanvas() {
        canvas.clear();
    }

    public void listenEvent(int x, int y, String event) {
        selectedTool.listenEvent(x,y,event);
    }

    public void listenMouseEvent(MouseEvent e) {
        if(selectedTool != null)
            selectedTool.listenMouseEvent(e);
    }
}
