package com.iDebug.pickloose.network.server;

import com.google.gson.Gson;
import com.iDebug.pickloose.GameSettings;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;
import java.net.Socket;

class GetGameSettingsDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        GameSettings gameSettings = Manager.getInstance().getGameSettings();
        String body = new Gson().toJson(gameSettings);
        individualRespond(new Response(request.getService(), FEEDBACK.SUCCEED, body),socket);
    }
}
