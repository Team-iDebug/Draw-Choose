package com.iDebug.pickloose.canvas;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

class ColorController {
    static Color color = Color.BLACK;
    private ColorController() {

    }
    static void setColor(Color color) {
        ColorController.color = color;
    }
    static Color getColor() {
        return color;
    }
    public static Image reColor(Image inputImage, Color sourceColor, Color finalColor) {
        int            W           = (int) inputImage.getWidth();
        int            H           = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(W, H);
        PixelReader reader      = inputImage.getPixelReader();
        PixelWriter writer      = outputImage.getPixelWriter();
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                Color currColor = reader.getColor(x,y);
                if(currColor == sourceColor)
                    writer.setColor(x,y,finalColor);
                else
                    writer.setColor(x,y,currColor);
            }
        }
        return outputImage;
    }
}
