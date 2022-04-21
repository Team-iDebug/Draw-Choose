package com.example.idebug.network.server;

import com.example.idebug.network.Request;
import com.example.idebug.network.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Dispatcher {
    abstract void dispatch(Request request, Socket socket) throws IOException;

    static void individualRespond(Response response, Socket socket) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(Response.deserialize(response));
        out.flush();
    }

    static void broadcastRespond(Response response) {
        var clients = ClientManager.getInstance().getClients();
        clients.forEach((user,socket) -> {
            System.out.println("ami eikhane...");
            try {
                individualRespond(response,socket);
                System.out.println(user.getUsername() + " is sent.");
            } catch (IOException e) {
                // janina ki korbo
                System.out.println("golmaal");
            }
        });
    }

    static void broadcastExcludeRespond(Response response,Socket...sockets) throws IOException {
        var clients = ClientManager.getInstance().getClients();
        HashSet<Socket> _sockets = new HashSet<>();
        for (var socket : sockets)
            _sockets.add(socket);
        clients.forEach((user,socket) -> {
            if(!_sockets.contains(socket)) {
                try {
                    individualRespond(response,socket);
                } catch (IOException e) {
                    // janina ki korbo
                }
            }
        });
    }
}
