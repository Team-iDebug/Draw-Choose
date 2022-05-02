package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.LobbyManager;
import com.iDebug.pickloose.network.Response;

class SetReadyDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        try {
            System.out.println("Client : " + response.getBody());
            AuthUser user = new Gson().fromJson(response.getBody(),AuthUser.class);
            System.out.println(user.getUserid());
            LobbyManager.getInstance().setReady(user);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
