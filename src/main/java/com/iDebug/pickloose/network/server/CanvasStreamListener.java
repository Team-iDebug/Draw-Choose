package com.iDebug.pickloose.network.server;

import javafx.application.Platform;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

class CanvasStreamListener extends Listener {
    CanvasStreamListener(Socket socket) throws IOException {
        super(socket);
    }

    private void broadCastStream(String toolAction, Socket excludeSocket) {
        Platform.runLater(()-> {
            var clients = Manager.getInstance().getCanvasUserSockets();
            clients.forEach((socket)->{
                if(!socket.equals(excludeSocket)) {
                    try {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                        out.println(toolAction);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    @Override
    void handle() {
        Thread t = new Thread(() -> {
            boolean alive = true;

            /*
            System.out.println("----------server-----------");
            System.out.println("server canvas socket listening");
            System.out.println("----------------------------");
             */

            while (alive) {
                try {
                    String toolAction = in.readLine();
                    broadCastStream(toolAction,socket);
                    System.out.println(toolAction);
                } catch (IOException e) {
                    e.printStackTrace();
                    alive = false;
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }
}
