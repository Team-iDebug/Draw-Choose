package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.NetworkManager;
import com.iDebug.pickloose.network.JsonSerializer;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;

class CanvasSocketDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        try {
            Client client = (Client) new JsonSerializer().serialize(response.getBody());
            new CanvasStreamListener(client.socket);
            NetworkManager.getInstance().setStreamClient(client);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
