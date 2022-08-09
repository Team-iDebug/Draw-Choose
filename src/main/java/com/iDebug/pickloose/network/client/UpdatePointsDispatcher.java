package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iDebug.pickloose.GameManager;
import com.iDebug.pickloose.network.Response;
import javafx.application.Platform;

public class UpdatePointsDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        JsonObject body = new Gson().fromJson(response.getBody(), JsonObject. class);
        String userId = body.get("player").getAsString();
        int points = body.get("points").getAsInt();
        Platform.runLater(()->{GameManager.getInstance().updatePlayerGUIPoints(userId, points);});
    }
}
