package com.iDebug.pickloose.network.client;

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
            String data = null;
            while (alive) {
                try {
                    /*
                        data = posX,posY,width,height,mouse event type
                     */
                    data = in.readLine();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    alive = false;
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
