package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.iDebug.pickloose.GameSettings;
import com.iDebug.pickloose.LobbyManager;
import com.iDebug.pickloose.network.Response;

class GetGameSettingsDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        try {
            GameSettings gameSettings = new Gson().fromJson(response.getBody(),GameSettings.class);
            LobbyManager.getInstance().setGuiGameSettings(gameSettings);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}