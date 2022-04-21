package com.example.idebug.network.client;

import com.example.idebug.AuthUser;
import com.example.idebug.Player;
import com.example.idebug.User;
import com.example.idebug.network.Response;

class AddUserDispather {
    public void dispatch(Response response) { // responses with auth user data
        Player.getInstance().setAuthUser(AuthUser.serialize(response.getBody()));
    }
}
