package com.iDebug.pickloose.canvas;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Stack;

class Fill extends Tool {

    public static void doFill(GraphicsContext context, double x, double y, Color color) {
        WritableImage snap = context.getCanvas().snapshot(null,null);
        PixelWriter pixelWriter = context.getPixelWriter();
        PixelReader pixelReader = snap.getPixelReader();
        Color from = pixelReader.getColor((int)x,(int)y);
        int height = (int)context.getCanvas().getHeight();
        int width = (int)context.getCanvas().getWidth();
        int moves[][] = {{1,0},{-1,0},{0,-1},{0,1}};
        boolean visited[][] = new boolean[(int)height+10][(int)width+10];
        Stack<Point2D> dfsStack = new Stack<>();
        dfsStack.push(new Point2D(x,y));
        while (!dfsStack.empty()) {
            int currX = (int)dfsStack.peek().getX();
            int currY = (int)dfsStack.peek().getY();
            dfsStack.pop();
            if (currX >= width || currX < 0)
                continue;
            if (currY >= height || currY < 0)
                continue;
            if (visited[currY][currX])
                continue;
            if (!pixelReader.getColor(currX,currY).equals(from))
                continue;
            if (pixelReader.getColor(currX,currY).equals(color))
                continue;
            visited[currY][currX] = true;
            pixelWriter.setColor(currX,currY, color);
            for(var move : moves) {
                int nextX = currX + move[0];
                int nextY = currY + move[1];
                dfsStack.push(new Point2D(nextX,nextY));
            }
        }
    }

    @Override
    public void operate() {
        Point2D location = DrawManager.getInstance().getMouseLocation();
        double x = location.getX();
        double y = location.getY();
        doFill(DrawManager.getInstance().getGraphicsContext(),x,y,ColorController.getColor());
    }

    @Override
    public void listenMouseEvent(MouseEvent e) {
        DrawManager.getInstance().updateAndOperate(e.getX(),e.getY());
    }
}
