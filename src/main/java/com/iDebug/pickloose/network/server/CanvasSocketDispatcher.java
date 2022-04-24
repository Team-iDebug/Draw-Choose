package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.network.JsonDeserializer;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.client.Client;
import javafx.application.Platform;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CanvasSocketDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            Platform.runLater(() -> {
                Server server = new Server(9696, CanvasStreamListener.class);
                server.start();
                ServerSocket canvasStreamSocket = server.getServer();
                System.out.println(canvasStreamSocket.getLocalSocketAddress() + " " + canvasStreamSocket.getLocalPort());
                out.println(new JsonDeserializer().deserialize(canvasStreamSocket));
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
