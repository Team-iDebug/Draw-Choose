package com.example.idebug.network.client;

import com.example.idebug.network.Response;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;

class Dispatcher extends Thread {
    Socket socket;
    String msg;
    Dispatcher(Socket socket, String msg) throws IOException {
        this.socket = socket;
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            Response res = Response.serialize(msg);
            switch (res.getService()) {
                case ADD_USER -> Platform.runLater(() -> {
                    new AddUserDispather().dispatch(res);
                });
                case NEW_MESSAGE -> Platform.runLater(() -> {
                    new NewMessageDispatcher().dispatch(res);
                });
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
