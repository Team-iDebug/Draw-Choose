package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.GameSettings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class Manager {
    /**
     *  The user mapping implementation needs to replaced with database.
     */
    private HashMap<Socket, AuthUser> socketAuthUserMapping;
    private HashMap<String, AuthUser>  tokenAuthUserMapping;
    private ArrayList<AuthUser> users;
    private HashMap<String, Socket> tokenSocketMapping;
    private HashSet<String> readyUsers; // token
    private static Manager manager;
    private ServerSocket gameServerSocket;
    private ServerSocket streamServerSocket;
    private AuthUser host;
    private GameSettings gameSettings;

    private Manager() {
        users = new ArrayList<>();
        socketAuthUserMapping = new HashMap<>();
        tokenAuthUserMapping = new HashMap<>();
        tokenSocketMapping = new HashMap<>();
        readyUsers = new HashSet<>();
    }
    static Manager getInstance() {
        if(manager == null)
            manager = new Manager();
        return manager;
    }
    void add(AuthUser user, Socket socket) throws IOException {
        socketAuthUserMapping.put(socket, user);
        tokenAuthUserMapping.put(user.getToken(), user);
        tokenSocketMapping.put(user.getToken(),socket);
        users.add(user);
    }

    void remove(AuthUser user) {
        if(tokenSocketMapping.containsKey(user.getToken()))
            tokenSocketMapping.remove(user.getToken());
        users.remove(user);
    }

    HashMap<String, Socket> getClients() {
        return tokenSocketMapping;
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
            streamServerSocket = server.getSocket();
        }
        return streamServerSocket;
    }

    public ArrayList<AuthUser> getUsers() {
        return users;
    }

    public AuthUser getUser(Socket socket) {
        return socketAuthUserMapping.get(socket);
    }

    public AuthUser getHost() {
        return host;
    }

    public void setHost(AuthUser sender) {
        host = sender;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setReady(AuthUser user) {
        readyUsers.add(user.getToken());
    }

    public void setNotReady(AuthUser user) {readyUsers.remove(user.getToken());}

    public boolean isAllReady() {
        System.out.println(users.size() + " " + readyUsers.size());
        for(var u : users) {
            if(!readyUsers.contains(u.getToken()))
                return false;
        }
        return true;
    }

    public boolean isHost(AuthUser authSender) {
        return (authSender.getToken().equals(host.getToken()));
    }
}
