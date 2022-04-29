package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.SCENES;
import com.iDebug.pickloose.WindowManager;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Response;

class StartGameDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        if(response.getStatus() == FEEDBACK.SUCCEED) {
            WindowManager.getInstance().setScene(SCENES.GAME);
        }
        else {
            System.out.println("All player not ready");
        }
    }
}
