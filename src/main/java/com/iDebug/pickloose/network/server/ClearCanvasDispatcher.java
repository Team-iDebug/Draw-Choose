package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;
import com.iDebug.pickloose.network.SERVICE;

import java.io.IOException;
import java.net.Socket;

public class ClearCanvasDispatcher extends Dispatcher {
    @Override
    protected void dispatch(Request request, Socket socket) throws IOException {
        broadcastRespond(new Response(SERVICE.CLEAR_CANVAS, FEEDBACK.SUCCEED));
    }
}
