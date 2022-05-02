package com.iDebug.pickloose.network.server;

import javafx.application.Platform;

import java.net.ServerSocket;
import java.net.Socket;

/*
    1. get Request - Listener
    2. create Response - Handler
    3. send Response - Writer
 */

public class Server extends Thread {
    private ServerSocket server;
    protected Class requestListener;
    private int port;

    public Server(int port, Class requestListener) {
        this.port = port;
        this.requestListener = requestListener;
        try {
            server = new ServerSocket(port);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        setDaemon(true);
    }
    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.accept();
                Listener listener = (Listener) requestListener.getDeclaredConstructor(Socket.class).newInstance(socket);
                // add canvas stream clients to the server data
                if(listener.getClass().equals(CanvasStreamListener.class)) {
                    Manager.getInstance().addCanvasUser(socket);
                }
                listener.startListening();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ServerSocket getSocket() {
        return server;
    }
}
