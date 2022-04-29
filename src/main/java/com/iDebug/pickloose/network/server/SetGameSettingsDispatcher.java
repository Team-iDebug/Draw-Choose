package com.iDebug.pickloose.network.server;

import com.google.gson.Gson;
import com.iDebug.pickloose.GameSettings;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;
import java.net.Socket;

class SetGameSettingsDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        GameSettings gameSettings = new Gson().fromJson(request.getBody(),GameSettings.class);
        Manager.getInstance().setGameSettings(gameSettings);
        broadcastRespond(new Response(request.getService(), FEEDBACK.SUCCEED,request.getBody()));
    }
}