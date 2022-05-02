package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.UserManager;
import com.iDebug.pickloose.network.Response;

import java.sql.SQLException;

public class RemoveUserDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        AuthUser user = new Gson().fromJson(response.getBody(), AuthUser.class);
        try {
            UserManager.getInstance().remove(user);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
