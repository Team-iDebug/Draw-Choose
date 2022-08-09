package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.PlayTimer;
import com.iDebug.pickloose.network.Response;
import javafx.application.Platform;

public class StopMatchDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        Platform.runLater(()->{
            PlayTimer.setFinished();
        });
    }
}
