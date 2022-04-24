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
            System.out.println("canvas stream started...");
            try {
                String data = null;
                while (true) {
                    data = in.readLine();
                    System.out.println(data);
                }
            } catch (IOException ex) {
                //TODO make a callback on exception.
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
