package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.UserManager;
import com.iDebug.pickloose.network.Response;

public class AddUserDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        try {
            AuthUser user = new Gson().fromJson(response.getBody(),AuthUser.class);
            UserManager.getInstance().add(user);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
