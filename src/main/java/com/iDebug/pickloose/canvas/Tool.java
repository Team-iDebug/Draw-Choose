package com.iDebug.pickloose.canvas;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;


abstract class Tool {
    public abstract void operate();
    public abstract void listenMouseEvent(MouseEvent e);

    public static Image createTexture(Shape shape, String texture, double opacity) {
        Image textureImage = new Image(texture);
        shape.setOpacity(opacity);
        shape.setFill(new ImagePattern(textureImage));
        return createImage(shape);
    }

    public static Image createTexture(Shape shape, String texture, Color from, Color to, double opacity) {
        Image textureImage = ColorController.reColor(new Image(texture),from, to);
        shape.setOpacity(opacity);
        shape.setFill(new ImagePattern(textureImage));
        return createImage(shape);
    }

    public static Image createImage(Node node) {
        WritableImage wi;

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        int imageWidth = (int) node.getBoundsInLocal().getWidth();
        int imageHeight = (int) node.getBoundsInLocal().getHeight();

        wi = new WritableImage(imageWidth, imageHeight);
        node.snapshot(parameters, wi);

        return wi;
    }
}
