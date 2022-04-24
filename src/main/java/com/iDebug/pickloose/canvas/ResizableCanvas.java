package com.iDebug.pickloose.canvas;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ResizableCanvas extends Canvas {
    private TimeStack<Image> operationStack;
    private WritableImage updatedSnap;
    private WritableImage copySnap;

    public ResizableCanvas() {
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
        operationStack = new TimeStack<>();
    }

    private void draw() {
        double width = getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);

        if(updatedSnap == null)
            return;
        if(width < updatedSnap.getWidth())
            gc.drawImage(updatedSnap, 0, 0, width, height);
        else {
            gc.drawImage(updatedSnap, width/2 -  updatedSnap.getWidth()/2, height/2 - updatedSnap.getHeight()/2);
        }
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

    public void fill(Image image) {
        getGraphicsContext2D().drawImage(image,getWidth() - image.getWidth()/2, getHeight() - image.getHeight()/2);
    }

    public void copy() {
        copySnap = new WritableImage((int)getWidth(),(int)getHeight());
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        snapshot(snapshotParameters, copySnap);
    }

    public void paste() {
        fill(copySnap);
    }

    public void undo() {
        Image image = operationStack.backward();
        GraphicsContext gc = getGraphicsContext2D();
        if(image == null) {
            gc.clearRect(0,0,getWidth(), getHeight());
        }
        else {
            gc.drawImage(image,0,0);
        }
    }

    public void redo() {
        Image image = operationStack.forward();
        GraphicsContext gc = getGraphicsContext2D();
        if(image == null) {
            gc.clearRect(0,0,getWidth(), getHeight());
        }
        else {
            gc.drawImage(image,0,0);
        }
    }

    public WritableImage updateSnap() {
        updatedSnap = new WritableImage((int)getWidth(), (int)getHeight());
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        return snapshot(snapshotParameters,updatedSnap);
    }

    public void updateStack() {
        WritableImage wi = new WritableImage((int)getWidth(), (int)getHeight());
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        operationStack.forward(snapshot(snapshotParameters,wi));
    }
}
