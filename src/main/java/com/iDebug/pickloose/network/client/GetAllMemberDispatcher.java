package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.LobbyManager;
import com.iDebug.pickloose.UserManager;
import com.iDebug.pickloose.network.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;

class GetAllMemberDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        try {
            Type listOfAuthUserObject = new TypeToken<ArrayList<AuthUser>>() {}.getType();
            ArrayList<AuthUser> users = new Gson().fromJson(response.getBody(),listOfAuthUserObject);
            for(var user : users) {
                UserManager.getInstance().add(user);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
