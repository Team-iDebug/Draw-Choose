package com.iDebug.pickloose.canvas;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

class ColorController {
    private ColorController() {

    }
    public static Image reColor(Image inputImage, Color sourceColor, Color finalColor) {
        /*
            Fix required : Color not changing
         */

        int            W           = (int) inputImage.getWidth();
        int            H           = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(W, H);
        PixelReader reader      = inputImage.getPixelReader();
        PixelWriter writer      = outputImage.getPixelWriter();

        for(int y = 0 ; y < H ; y++) {
            for(int x = 0 ; x < W ; x++) {
//                Color currColor = reader.getColor(x,y);
//                if(currColor.equals(sourceColor))
                    writer.setColor(x,y,finalColor);
//                else
//                    writer.setColor(x,y,currColor);
            }
        }

        /*
        float          ocR         = (float) sourceColor.getRed();
        float          ocG         = (float) sourceColor.getGreen();
        float          ocB         = (float) sourceColor.getBlue();
        float          ncR         = (float) finalColor.getRed();
        float          ncG         = (float) finalColor.getGreen();
        float          ncB         = (float) finalColor.getBlue();
        java.awt.Color oldColor    = new java.awt.Color(ocR, ocG, ocB);
        java.awt.Color newColor    = new java.awt.Color(ncR, ncG, ncB);
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                int            argb       = reader.getArgb(x, y);
                java.awt.Color pixelColor = new java.awt.Color(argb, true);
                writer.setArgb(x, y,
                        pixelColor.equals(oldColor) ?
                                newColor.getRGB() :
                                pixelColor.getRGB());
            }
        }
        */

        return outputImage;
    }
}
