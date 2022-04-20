package com.idebug.server;

import com.idebug.FEEDBACK;
import com.idebug.Request;
import com.idebug.Response;

import java.io.IOException;
import java.net.Socket;

class NewMessageDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        Response response = new Response(request.getService(), FEEDBACK.SUCCEED,request.getBody());
        broadcastRespond(response);
    }
}
