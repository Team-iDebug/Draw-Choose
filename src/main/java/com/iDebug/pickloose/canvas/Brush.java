package com.iDebug.pickloose.canvas;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

abstract class Brush extends Tool {
    protected String textureFile;
    protected double radius;
    protected double opacity;
    protected Shape shape;
    protected Image texture;

    @Override
    public void listenMouseEvent(MouseEvent e) {
        if(MouseEvent.MOUSE_DRAGGED.equals(e.getEventType())) {
            DrawManager.getInstance().updateAndOperate(e.getX(),e.getY());
        }
        else if (MouseEvent.MOUSE_PRESSED.equals(e.getEventType())) {
            DrawManager.getInstance().updateMouseLocation(e.getX(), e.getY());
        }
    }

    protected static Image createTexture(Brush brush) {
        return createTexture(brush.getShape(),brush.getTextureFile(),brush.opacity);
    }

    protected static Image createTexture(Brush brush, Color from, Color to) {
        return createTexture(brush.getShape(), brush.getTextureFile(), from, to, brush.getOpacity());
    }

    /*
        Source : Wikipedia
        bresenham line drawing algorithm
     */
    protected static void bresenhamLine(Brush brush, double x0, double y0, double x1, double y1)
    {
        double dx =  Math.abs(x1-x0), sx = x0<x1 ? 1. : -1.;
        double dy = -Math.abs(y1-y0), sy = y0<y1 ? 1. : -1.;
        double err = dx+dy, e2; /* error value e_xy */

        while( true){
            DrawManager.getInstance().getGraphicsContext().drawImage(
                    brush.getTexture(),
                    x0 - brush.getRadius()/2,
                    y0 - brush.getRadius()/2);
            if (x0==x1 && y0==y1) break;
            e2 = 2.*err;
            if (e2 > dy) { err += dy; x0 += sx; } /* e_xy+e_x > 0 */
            if (e2 < dx) { err += dx; y0 += sy; } /* e_xy+e_y < 0 */
        }
    }

    public double getSize() {
        return radius;
    }
    public void setSize(double size) {
        this.radius = size;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public double getRadius() {
        return radius;
    }

    public Shape getShape() {
        return shape;
    }

    public Image getTexture() {
        return texture;
    }

    public String getTextureFile() {
        return textureFile;
    }

    public void setTextureFile(String textureFile) {
        this.textureFile = textureFile;
    }
}
