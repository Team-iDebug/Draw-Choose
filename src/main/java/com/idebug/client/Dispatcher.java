package com.idebug.client;

import com.idebug.Response;

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
                case ADD_USER -> new AddUserDispather().dispatch(res);
                case NEW_MESSAGE -> new NewMessageDispatcher().dispatch(res);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
