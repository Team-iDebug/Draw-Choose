package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.canvas.DrawManager;
import com.iDebug.pickloose.network.Response;
import javafx.scene.paint.Color;

public class SetColorDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        Color color = Color.valueOf(response.getBody());
        DrawManager.getInstance().setSelectedColor(color);
    }
}
