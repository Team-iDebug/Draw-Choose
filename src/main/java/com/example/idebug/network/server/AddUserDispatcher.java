package com.example.idebug.network.server;

import com.example.idebug.AuthUser;
import com.example.idebug.User;
import com.example.idebug.network.*;

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
