package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.canvas.DrawManager;
import com.iDebug.pickloose.network.Response;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;

class UpdateCanvasDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        try {
            File file = new File("snap");
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(response.getBody());
            BufferedImage snap = ImageIO.read(file);
            WritableImage writableImage = new WritableImage(snap.getWidth(),snap.getHeight());
            PixelWriter pw = writableImage.getPixelWriter();
            for (int x = 0; x < snap.getWidth(); x++) {
                for (int y = 0; y < snap.getHeight(); y++) {
                    pw.setArgb(x, y, snap.getRGB(x, y));
                }
            }
            DrawManager.getInstance().updateCanvas(writableImage);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
