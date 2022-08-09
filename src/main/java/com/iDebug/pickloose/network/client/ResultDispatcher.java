package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.iDebug.pickloose.UserManager;
import com.iDebug.pickloose.WindowManager;
import com.iDebug.pickloose.network.Response;

import java.util.HashMap;

public class ResultDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        Gson gson = new Gson();
        HashMap<String,Object> map = new HashMap<>();
        map = (HashMap<String,Object>) gson.fromJson(response.getBody(), map.getClass());
        if(map == null || map.size() == 0)
            return;
        WindowManager.getInstance().showResult(map);
    }
}
