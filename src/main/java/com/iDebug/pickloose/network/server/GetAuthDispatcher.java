package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.User;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

class GetAuthDispatcher extends Dispatcher {
    @Override
    protected void dispatch(Request request, Socket socket) throws IOException {
        AuthUser user = new AuthUser(request.getSender());
        try {
            Manager.getInstance().add(user, socket);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        Response response = new Response(request.getService(), FEEDBACK.SUCCEED, User.deserialize(user));
        individualRespond(response,socket);
    }
}
