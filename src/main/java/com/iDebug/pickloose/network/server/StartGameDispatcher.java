package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;
import com.iDebug.pickloose.network.SERVICE;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

class StartGameDispatcher extends Dispatcher {

    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        try {
            if(Manager.getInstance().isHost(request.getAuthSender()) && Manager.getInstance().isAllReady()) {
                broadcastRespond(new Response(SERVICE.START_GAME, FEEDBACK.SUCCEED));
            }
            else {
                individualRespond(new Response(SERVICE.START_GAME, FEEDBACK.FAILED), socket);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
