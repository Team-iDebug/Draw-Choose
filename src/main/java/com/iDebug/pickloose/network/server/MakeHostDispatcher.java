package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.network.Request;

import java.io.IOException;
import java.net.Socket;

class MakeHostDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        if(Manager.getInstance().getHost() == null)
            Manager.getInstance().setHost(request.getAuthSender());
        Manager.getInstance().setReady(request.getAuthSender());
    }
}
