package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.canvas.DrawManager;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;

public class CanvasStreamListener extends Listener {
    CanvasStreamListener(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    void handle() {
        Thread t = new Thread(() -> {
            boolean alive = true;
            String info = null;
            while (alive) {
                try {
                    /*
                        data = posX,posY,width,height,mouse event type
                     */
                    info = in.readLine();
                    String[] data  = info.split(",");
                    double posX = Double.parseDouble(data[0]);
                    double posY = Double.parseDouble(data[1]);
                    double width = Double.parseDouble(data[2]);
                    double height = Double.parseDouble(data[3]);
                    String event = data[4];
                    double currWidth = DrawManager.getInstance().getCanvas().getWidth();
                    double currHeight = DrawManager.getInstance().getCanvas().getHeight();
                    int currX = (int) Math.rint((posX*currWidth)/width);
                    int currY = (int) Math.rint((posY*currHeight)/height);
                    Platform.runLater(() -> {
                        DrawManager.getInstance().listenEvent(currX,currY,event);
                    });
                }
                catch (IOException e) {
                    alive = false;
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
