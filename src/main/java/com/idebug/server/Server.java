package com.idebug.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
    1. get Request - Listener
    2. create Response - Handler
    3. send Response - Writer
 */

public class Server extends Thread {
    private ServerSocket server;
    public Server() throws IOException {
        server = new ServerSocket(6969);
    }
    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.accept();
                new ServerListener(socket).start();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            new Server().start();
        }
        catch (Exception e) {
//            e.printStackTrace();

        }
    }
}
