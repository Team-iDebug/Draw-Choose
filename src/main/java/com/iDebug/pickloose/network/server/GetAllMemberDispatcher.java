package com.iDebug.pickloose.network.server;

import com.google.gson.Gson;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;
import com.iDebug.pickloose.network.SERVICE;

import java.io.IOException;
import java.net.Socket;

class GetAllMemberDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        var users = Manager.getInstance().getUsers();
        String body = new Gson().toJson(users);
        individualRespond(new Response(request.getService(), FEEDBACK.SUCCEED,body),socket);
    }
}
