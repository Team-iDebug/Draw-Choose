package com.iDebug.pickloose.network.server;

import com.google.gson.Gson;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;
import com.iDebug.pickloose.network.SERVICE;

import java.io.IOException;
import java.net.Socket;

public class RemoveUserDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        AuthUser user = new Gson().fromJson(request.getBody(),AuthUser.class);
        Manager.getInstance().remove(user);
        Response response = new Response(SERVICE.DELETE_USER, FEEDBACK.SUCCEED, request.getBody());
        broadcastRespond(response);
    }

    void dispatch(AuthUser user) {
        Manager.getInstance().remove(user);
        String body = new Gson().toJson(user);
        Response response = new Response(SERVICE.DELETE_USER, FEEDBACK.SUCCEED, body);
        broadcastRespond(response);
    }
}
