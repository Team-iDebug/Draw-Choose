package com.iDebug.pickloose.network.server;

import com.google.gson.Gson;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;
import java.net.Socket;

class SetNotReadyDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        AuthUser sender = request.getAuthSender();
        Manager.getInstance().setNotReady(sender);
        String body = new Gson().toJson(sender) ;
        Response response = new Response(request.getService(), FEEDBACK.SUCCEED, body);
        broadcastRespond(response);
    }
}
