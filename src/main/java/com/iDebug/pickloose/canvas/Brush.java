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
    protected Color color;

    @Override
    public void listenEvent(int x, int y, String event) {
        if(event.equals("MOUSE_DRAGGED")) {
            DrawManager.getInstance().updateAndOperate(x,y,event);
        }
        else if(event.equals("MOUSE_PRESSED")) {
            DrawManager.getInstance().updateMouseLocation(x,y,event);
        }
    }

    @Override
    public void listenMouseEvent(MouseEvent e) {
        if(MouseEvent.MOUSE_DRAGGED.equals(e.getEventType())) {
            DrawManager.getInstance().updateAndOperate(e.getX(),e.getY(),e.getEventType());
        }
        else if (MouseEvent.MOUSE_PRESSED.equals(e.getEventType())) {
            DrawManager.getInstance().updateMouseLocation(e.getX(), e.getY(),e.getEventType() );
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
//    protected static void bresenhamLine(Brush brush, double x0, double y0, double x1, double y1)
//    {
//        double dx =  Math.abs(x1-x0), sx = x0<x1 ? 1. : -1.;
//        double dy = -Math.abs(y1-y0), sy = y0<y1 ? 1. : -1.;
//        double err = dx+dy, e2; /* error value e_xy */
//
//        while( true){
//            DrawManager.getInstance().getGraphicsContext().drawImage(
//                    brush.getTexture(),
//                    x0 - brush.getRadius()/2,
//                    y0 - brush.getRadius()/2);
//            if (x0==x1 && y0==y1) break;
//            e2 = 2.*err;
//            if (e2 > dy) { err += dy; x0 += sx; } /* e_xy+e_x > 0 */
//            if (e2 < dx) { err += dx; y0 += sy; } /* e_xy+e_y < 0 */
//        }
//    }

//    protected static void bresenhamLine(Brush brush, double x1, double y1, double x2, double y2)
//    {
//        Double m_new = 2 * (y2 - y1);
//        Double slope_error_new = m_new - (x2 - x1);
//        for (Double x = x1, y = y1; x <= x2; x++)
//        {
//            DrawManager.getInstance().getGraphicsContext().drawImage(
//                    brush.getTexture(),
//                    x - brush.getRadius()/2,
//                    y - brush.getRadius()/2);
//
//            slope_error_new += m_new;
//
//            if (slope_error_new >= 0)
//            {
//                y++;
//                slope_error_new  -= 2 * (x2 - x1);
//            }
//        }
//    }

    protected static void bresenhamLine(Brush brush, double x0, double y0, double x1, double y1)
    {
        double dx = Math.abs(x1 - x0);
        double sx = x0 < x1 ? 1 : -1;
        double dy = -Math.abs(y1-y0);
        double sy = y0 < y1 ? 1 : -1;
        double error = dx + dy;
        while(true) {
            DrawManager.getInstance().getGraphicsContext().drawImage(
                    brush.getTexture(),
                    x0 - brush.getRadius()/2,
                    y0 - brush.getRadius()/2);
            if(x0 == x1 && y0 == y1)
                break;
            double e2 = 2*error;
            if(e2 >= dy) {
                if(x0 == x1)
                    break;
                error = error + dy;
                x0 = x0+sx;
            }
            if(e2 <= dx) {
                if(y0 == y1)
                    break;
                error = error + dx;
                y0 = y0 + sy;
            }
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
