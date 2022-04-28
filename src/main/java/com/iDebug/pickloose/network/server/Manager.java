package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.AuthUser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

class Manager {
    private ArrayList<OutputStream> clientOutputStreams;
    private HashMap<String, AuthUser>  UserMap;
    private ArrayList<AuthUser> users;
    private HashMap<String, Socket> socketMap;
    private static Manager manager;
    private ServerSocket gameServerSocket;
    private ServerSocket streamServerSocket;

    private Manager() {
        clientOutputStreams = new ArrayList<>();
        users = new ArrayList<>();
        socketMap = new HashMap<>();
    }
    static Manager getInstance() {
        if(manager == null)
            manager = new Manager();
        return manager;
    }
    void add(AuthUser user, Socket socket) throws IOException {
        socketMap.put(user.getToken(),socket);
        users.add(user);
        clientOutputStreams.add(socket.getOutputStream());
    }
    HashMap<String, Socket> getClients() {
        return socketMap;
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

    public ArrayList<AuthUser> getUsers() {
        return users;
    }
}
