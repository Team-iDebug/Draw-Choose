package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.User;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;
import java.net.Socket;

class AddUserDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        AuthUser user = new AuthUser(request.getSender());
        ClientManager.getInstance().add(user, socket);
        Response response = new Response(request.getService(), FEEDBACK.SUCCEED, User.deserialize(user));
        individualRespond(response,socket);
    }
}
