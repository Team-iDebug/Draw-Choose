package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.NetworkManager;
import com.iDebug.pickloose.network.Response;
import com.iDebug.pickloose.network.SERVICE;

class GetAuthDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) { // responses with auth user data
        try {
            AuthUser user = new Gson().fromJson(response.getBody(),AuthUser.class);
            NetworkManager.getInstance().setAuthUser(user);
            NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.ADD_USER,response.getBody());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}