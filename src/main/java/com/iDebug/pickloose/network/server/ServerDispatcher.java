package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.network.Request;

import java.io.IOException;
import java.net.Socket;

public class ServerDispatcher extends Thread {
    Socket socket;
    String msg;

    ServerDispatcher(Socket socket, String req) throws IOException {
        this.socket = socket;
        this.msg = req;
    }

    public void dispatch(Request req) {
        try {
            switch (req.getService()) {
                case ADD_USER -> new AddUserDispatcher().dispatch(req, socket);
                case NEW_MESSAGE -> new NewMessageDispatcher().dispatch(req, socket);
                case UPDATE_CANVAS -> new UpdateCanvasDispather().dispatch(req,socket);
            }
        }
        catch (IOException e) {

        }
        catch (NullPointerException e) {

        }
        catch (Exception e) {

        }
    }

    @Override
    public void run() {
        dispatch(Request.serialize(msg));
    }
}
