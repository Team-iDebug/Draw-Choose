package com.example.idebug.network.server;

import com.example.idebug.AuthUser;

import java.net.Socket;
import java.util.HashMap;

class ClientManager {
    private HashMap<AuthUser, Socket> clients;
    private static ClientManager clientManager;
    private ClientManager() {
        clients = new HashMap<>();
    }
    static ClientManager getInstance() {
        if(clientManager == null)
            clientManager = new ClientManager();
        return clientManager;
    }
    void add(AuthUser user, Socket socket) {
        clients.put(user,socket);
    }
    HashMap<AuthUser, Socket> getClients() {
        return clients;
    }
}
