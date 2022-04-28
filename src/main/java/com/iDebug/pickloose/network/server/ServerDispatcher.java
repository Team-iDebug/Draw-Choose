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
                case GET_AUTH -> new GetAuthDispatcher().dispatch(req, socket);
                case ADD_USER -> new AddUserDispatcher().dispatch(req,socket);
                case NEW_MESSAGE -> new NewMessageDispatcher().dispatch(req, socket);
                case UPDATE_CANVAS -> new UpdateCanvasDispather().dispatch(req,socket);
                case GET_CANVAS_SOCKET -> new CanvasSocketDispatcher().dispatch(req,socket);
                case GET_ALL_MEMEBER -> new GetAllMemberDispatcher().dispatch(req,socket);
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
