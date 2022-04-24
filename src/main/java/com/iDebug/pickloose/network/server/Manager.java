package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.network.client.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

class Manager {
    private ArrayList<OutputStream> clientOutputStreams;
    private HashMap<AuthUser, Socket> clients;
    private static Manager manager;
    private ServerSocket gameServerSocket;
    private ServerSocket streamServerSocket;

    private Manager() {
        clients = new HashMap<>();
    }
    static Manager getInstance() {
        if(manager == null)
            manager = new Manager();
        return manager;
    }
    void add(AuthUser user, Socket socket) throws IOException {
        clients.put(user,socket);
        clientOutputStreams.add(socket.getOutputStream());
    }
    HashMap<AuthUser, Socket> getClients() {
        return clients;
    }

    public ArrayList<OutputStream> getClientOutputStreams() {
        return clientOutputStreams;
    }

    public ServerSocket getCanvasStream() {
        if(streamServerSocket == null) {
            Server server = null;
            try {
                server = new Server(0, CanvasStreamListener.class);
                server.start();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            streamServerSocket = server.getServer();
        }
        return streamServerSocket;
    }
}
