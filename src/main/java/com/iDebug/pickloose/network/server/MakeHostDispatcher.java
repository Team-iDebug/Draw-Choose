package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

class MakeHostDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        try {
            if(!Manager.getInstance().hostAssigned()) {
                Manager.getInstance().setHost(request.getAuthSender());
                Manager.getInstance().setReady(request.getAuthSender());
                individualRespond(new Response(request.getService(), FEEDBACK.SUCCEED),socket);
            }
            else {
                individualRespond(new Response(request.getService(), FEEDBACK.FAILED), socket);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
