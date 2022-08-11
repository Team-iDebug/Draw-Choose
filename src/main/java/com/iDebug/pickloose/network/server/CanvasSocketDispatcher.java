package com.iDebug.pickloose.network.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iDebug.pickloose.network.*;
import com.iDebug.pickloose.network.client.CanvasStreamListener;
import com.iDebug.pickloose.network.client.Client;

import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class CanvasSocketDispatcher extends Dispatcher {
    @Override
    protected void dispatch(Request request, Socket socket) {
        try {
            /*
                body = {
                           "ip" : ...,
                           "port" : ...,
                        }
             */

            ServerSocket streamServerSocket = Manager.getInstance().getCanvasStream();
            String ip = Inet4Address.getLocalHost().getHostAddress();
            String port = String.valueOf(streamServerSocket.getLocalPort());

            /*
            System.out.println("----------server-----------");
            System.out.println("ip : " + ip);
            System.out.println("port : " + port);
            System.out.println("---------------------------");
             */

            JsonObject bodyObject = new JsonObject();
            bodyObject.addProperty("ip",ip);
            bodyObject.addProperty("port",port);
            String body = new Gson().toJson(bodyObject,JsonObject.class);
            Response response = new Response(SERVICE.GET_CANVAS_SOCKET, FEEDBACK.SUCCEED, body);
            individualRespond(response,socket);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
