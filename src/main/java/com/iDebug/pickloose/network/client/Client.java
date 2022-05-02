package com.iDebug.pickloose.network.client;

import com.google.gson.annotations.Expose;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/*
    1. send Req - Writer
    2. get Response - Listener
    3. update local entities - Handler
 */

public class Client {
    @Expose
    private String ip;
    @Expose
    private int port;
    @Expose
    private Class responseListener;
    Socket socket;

    public Client(String ip, int port, Class listener) {
        this.ip = ip;
        this.port = port;
        this.responseListener = listener;
    }

    public void start() {
        try {
            socket = new Socket(ip,port);
            Listener listener = (Listener) responseListener.getDeclaredConstructor(Socket.class).newInstance(socket);
            listener.startListening();
        }
        catch (NullPointerException e) {
            System.out.println("connection not found!!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String request) {
        try {
            new Writer(socket, request).start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendToolAction(String toolAction) {
        try {
            Thread thread = new Thread(() ->{
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                    out.println(toolAction);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            thread.setDaemon(true);
            thread.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Socket getSocket() {
        return socket;
    }
}
