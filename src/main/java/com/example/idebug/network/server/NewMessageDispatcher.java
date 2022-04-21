package com.example.idebug.network.server;

import com.example.idebug.network.FEEDBACK;
import com.example.idebug.network.Request;
import com.example.idebug.network.Response;

import java.io.IOException;
import java.net.Socket;

class NewMessageDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        Response response = new Response(request.getService(), FEEDBACK.SUCCEED,request.getBody());
        broadcastExcludeRespond(response, socket);
    }
}
