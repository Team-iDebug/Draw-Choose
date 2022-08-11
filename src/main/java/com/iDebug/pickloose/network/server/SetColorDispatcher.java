package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;
import java.net.Socket;

public class SetColorDispatcher extends Dispatcher {

    @Override
    protected void dispatch(Request request, Socket socket) throws IOException {
        broadcastExcludeRespond(new Response(request.getService(), FEEDBACK.SUCCEED,request.getBody()), socket);
    }
}
