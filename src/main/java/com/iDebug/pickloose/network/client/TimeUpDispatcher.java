package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.WindowManager;
import com.iDebug.pickloose.network.Response;

public class TimeUpDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        WindowManager.getInstance().timeIsUp(response.getBody());
    }
}
