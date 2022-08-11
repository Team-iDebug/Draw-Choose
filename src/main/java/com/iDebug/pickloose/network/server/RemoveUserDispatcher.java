package com.iDebug.pickloose.network.server;

import com.google.gson.Gson;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;
import com.iDebug.pickloose.network.SERVICE;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class RemoveUserDispatcher extends Dispatcher {
    @Override
    protected void dispatch(Request request, Socket socket) throws IOException {
        AuthUser user = new Gson().fromJson(request.getBody(),AuthUser.class);
        try {
            Manager.getInstance().remove(user);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        Response response = new Response(SERVICE.DELETE_USER, FEEDBACK.SUCCEED, request.getBody());
        broadcastRespond(response);
    }

    void dispatch(AuthUser user) {
        try {
            Manager.getInstance().remove(user);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        String body = new Gson().toJson(user);
        Response response = new Response(SERVICE.DELETE_USER, FEEDBACK.SUCCEED, body);
        broadcastRespond(response);
    }
}
