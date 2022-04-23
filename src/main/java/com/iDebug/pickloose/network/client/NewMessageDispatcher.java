package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.GUIController;
import com.iDebug.pickloose.network.Response;

import java.net.Socket;

class NewMessageDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        try {
            GUIController.getInstance().handle(response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
