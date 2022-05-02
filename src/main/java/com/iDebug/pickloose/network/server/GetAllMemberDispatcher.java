package com.iDebug.pickloose.network.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;
import javafx.application.Platform;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

class GetAllMemberDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        try {
            var users = Manager.getInstance().getAllUserInfo();
            var roles = Manager.getInstance().getAllUserRole();
            var readyUsers = Manager.getInstance().getAllReadyPlayers();
            String rolesString = new Gson().toJson(roles);
            String usersString = new Gson().toJson(users);
            String readyUsersString = new Gson().toJson(readyUsers);
            HashMap<String,String> bodyJson = new HashMap<>();
            bodyJson.put("users",usersString);
            bodyJson.put("roles",rolesString);
            bodyJson.put("readyUsers",readyUsersString);
            String body = new Gson().toJson(bodyJson);
            individualRespond(new Response(request.getService(), FEEDBACK.SUCCEED,body),socket);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
