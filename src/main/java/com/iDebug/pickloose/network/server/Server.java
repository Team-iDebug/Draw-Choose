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
    }
    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.accept();
                System.out.println("client added...");
                Listener listener = (Listener) requestListener.getDeclaredConstructor(Socket.class).newInstance(socket);
                listener.startListening();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(0, GameServerListener.class);
            System.out.println(server.getServer().getLocalPort());
            server.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerSocket getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }
}
