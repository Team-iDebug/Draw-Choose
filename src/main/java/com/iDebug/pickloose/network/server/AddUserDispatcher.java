package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;
import java.net.Socket;

class AddUserDispatcher extends Dispatcher {
    @Override
    protected void dispatch(Request request, Socket socket) throws IOException {
        Response response = new Response(request.getService(), FEEDBACK.SUCCEED, request.getBody());
        broadcastRespond(response);
    }
}
