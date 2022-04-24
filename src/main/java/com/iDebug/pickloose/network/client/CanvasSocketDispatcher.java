package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.NetworkManager;
import com.iDebug.pickloose.network.JsonSerializer;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;

class CanvasSocketDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        Client client = (Client) new JsonSerializer().serialize(response.getBody(), Client.class);
        System.out.println(response.getBody());
        System.out.println("Paisi: " + client.getIp() + " " + client.getPort());
        NetworkManager.getInstance().setStreamClient(client);
        client.start();
    }
}
