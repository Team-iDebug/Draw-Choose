package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.NetworkManager;
import com.iDebug.pickloose.network.Response;

class AddUserDispather extends Dispatcher {
    @Override
    void dispatch(Response response) { // responses with auth user data
        try {
            NetworkManager.getInstance().setAuthUser(AuthUser.serialize(response.getBody()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
