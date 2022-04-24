package com.iDebug.pickloose.network.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

class CanvasStreamListener extends Listener {
    CanvasStreamListener(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    void handle() {
        Thread t = new Thread(() -> {
            try {
                String data = null;
                while (true) {
                    data = in.readLine();
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println(data);
                }
            } catch (IOException ex) {
                //TODO make a callback on exception.
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
