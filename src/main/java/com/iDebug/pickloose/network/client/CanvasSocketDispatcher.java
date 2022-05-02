package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iDebug.pickloose.NetworkManager;
import com.iDebug.pickloose.network.JsonSerializer;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;

class CanvasSocketDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        String body = response.getBody();
        JsonObject bodyJson = new Gson().fromJson(body,JsonObject.class);
        String ip = bodyJson.get("ip").getAsString();
        int port = bodyJson.get("port").getAsInt();

        /*
        System.out.println("----------client-----------");
        System.out.println("ip : " + ip);
        System.out.println("port : " + port);
        System.out.println("---------------------------");
         */

        Client client = new Client(ip,port,CanvasStreamListener.class);
        NetworkManager.getInstance().setStreamClient(client);
        client.start();
    }
}
