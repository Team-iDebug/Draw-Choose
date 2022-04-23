package com.iDebug.pickloose.canvas;

import com.iDebug.pickloose.NetworkManager;
import com.iDebug.pickloose.network.SERVICE;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

public class DrawManager {
    private static DrawManager drawManager;
    private Point2D mouseLocation;
    private Point2D prevMouseLocation;
    private Tool selectedTool;
    private Color selectedColor;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private double CANVAS_HEIGHT = 700;
    private double CANVAS_WIDTH = 1000;

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
        selectedTool.operate();
        WritableImage snap = canvas.snapshot(null,null);
        String msg = new String();
        try {
            ImageIO.write((RenderedImage) snap,msg,System.out);
            NetworkManager.sendReqAsAuthUser(SERVICE.UPDATE_CANVAS, msg);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

    public void setCanvas(Canvas canvas) {
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

    Image canvasSnap;
    TimeStack<Image> images;

    public void copyCanvas() {
        System.out.println("copied!!!");
        canvasSnap = canvas.snapshot(null,null);
    }

    public void pasteCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(canvasSnap,0,0);
    }

    public void undo() {
        Image image = images.backward();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if(image == null) {
            gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
        }
        else {
            gc.drawImage(image,0,0);
        }
    }

    public void redo() {
        Image image = images.forward();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if(image == null) {
            gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
        }
        else {
            gc.drawImage(image,0,0);
        }
    }

    public void listenMouseEvent(MouseEvent e) {
        selectedTool.listenMouseEvent(e);
    }
}
