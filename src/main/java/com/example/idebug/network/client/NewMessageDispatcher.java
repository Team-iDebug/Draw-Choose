package com.example.idebug.network.client;

import com.example.idebug.GUIController;
import com.example.idebug.network.Response;

class NewMessageDispatcher {
    void dispatch(Response response) {
        GUIController.getInstance().handle(response);
    }
}
