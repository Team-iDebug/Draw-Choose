package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.LobbyManager;
import com.iDebug.pickloose.network.Response;

class SetNotReadyDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        try {
            AuthUser user = new Gson().fromJson(response.getBody(),AuthUser.class);
            LobbyManager.getInstance().setNotReady(user);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}