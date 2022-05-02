package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.LobbyManager;
import com.iDebug.pickloose.USER_MODE;
import com.iDebug.pickloose.UserManager;
import com.iDebug.pickloose.network.Response;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class GetAllMemberDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        try {
            /*
                response = {
                    "users" : [{
                        ....,
                        ....
                    }],
                    "roles" : {
                        "..." : "...",
                    },
                    "readyUsers" : {
                        {...,...}
                    }
                }
             */
            JsonObject object = new Gson().fromJson(response.getBody(),JsonObject.class);
            String usersObject = object.get("users").getAsString();
            String rolesObject = object.get("roles").getAsString();
            String readyUsersObject = object.get("readyUsers").getAsString();
            Type listOfAuthUserObject = new TypeToken<ArrayList<AuthUser>>() {}.getType();
            ArrayList<AuthUser> users = new Gson().fromJson(usersObject,listOfAuthUserObject);
            Type mapOfRole = new TypeToken<HashMap<String, String>>() {}.getType();
            HashMap<String,String> roles = new Gson().fromJson(rolesObject,mapOfRole);
            Type setOfReadyUsers = new TypeToken<HashSet<String>>() {}.getType();
            HashSet<String> readyUsers = new Gson().fromJson(readyUsersObject,setOfReadyUsers);
            for(var user : users) {
                UserManager.getInstance().add(user);
            }
            roles.forEach((userid,role) -> {
                try {
                    UserManager.getInstance().setRole(userid,role);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            // GUI based
            for (var user : readyUsers) {
                if (!UserManager.getInstance().isHost(user))
                    LobbyManager.getInstance().setReady(user);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
