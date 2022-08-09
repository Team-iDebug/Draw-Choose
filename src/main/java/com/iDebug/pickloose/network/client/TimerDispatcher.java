package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.fxcontroller.FXCanvasController;
import com.iDebug.pickloose.network.Response;
import javafx.application.Platform;
import javafx.scene.control.Label;


public class TimerDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        Label guiTimer = FXCanvasController.getGuiTimer();
        Platform.runLater(() -> {
            guiTimer.setText(response.getBody());
        });
    }
}
