package com.iDebug.pickloose.network.client;

import com.google.gson.annotations.Expose;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.SERVICE;
import com.iDebug.pickloose.User;

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
    Socket socket;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void start() {
        try {
            socket = new Socket(ip,port);
            new GameClientListener(socket).startListening();
        }
        catch (Exception e) {
//            e.printStackTrace();
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

    public static void main(String[] args) {
        Client client = null;
        try {
            client = new Client("127.0.0.1", 6969);
            client.start();
        }
        catch (Exception e) {
//            e.printStackTrace();
            System.out.println("could not connect");
        }
        if(client != null) {
            User user = new User("nayemislamzr", "192.168.0.1");
            Request clientRequest = new Request(user, SERVICE.ADD_USER);
            String clientDeserializedReq = Request.deserialize(clientRequest);
            for(int i = 0 ; i < 10 ; i++) {
                client.sendMsg(clientDeserializedReq);
            }
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
