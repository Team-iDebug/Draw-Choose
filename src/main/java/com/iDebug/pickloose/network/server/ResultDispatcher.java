package com.iDebug.pickloose.network.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ResultDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        Gson gson = new Gson();
        HashMap<String,Object> map = new HashMap<>();
        map = (HashMap<String,Object>) gson.fromJson(request.getBody(), map.getClass());
        if(map == null || map.size() == 0)
            return;
        HashMap<String, Object> pointTable = new HashMap<>();
        map.forEach((userId,object) -> {
            try {
                String username = Manager.getInstance().getUser(userId).getUsername();
                pointTable.put(username,object);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        String json = gson.toJson(pointTable);
        broadcastRespond(new Response(request.getService(), FEEDBACK.SUCCEED,json));
    }
}
