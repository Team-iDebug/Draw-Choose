package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.canvas.DrawManager;
import com.iDebug.pickloose.network.Response;

public class ClearCanvasDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        DrawManager.getInstance().clearCanvas();
    }
}
