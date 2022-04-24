package com.iDebug.pickloose.network.server;

import com.google.gson.JsonObject;
import com.iDebug.pickloose.network.*;
import com.iDebug.pickloose.network.client.Client;

import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class CanvasSocketDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) {
        try {
            ServerSocket streamServerSocket = Manager.getInstance().getCanvasStream();
            System.out.println(streamServerSocket.getInetAddress().getHostAddress() + " " + streamServerSocket.getLocalPort());
            String body = new JsonDeserializer().deserialize(new Client(Inet4Address.getLocalHost().getHostAddress(), streamServerSocket.getLocalPort()));
            Response response = new Response(SERVICE.GET_CANVAS_SOCKET, FEEDBACK.SUCCEED, body);
            individualRespond(response,socket);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
