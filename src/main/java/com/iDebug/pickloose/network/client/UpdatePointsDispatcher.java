package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iDebug.pickloose.GameManager;
import com.iDebug.pickloose.network.Response;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class UpdatePointsDispatcher extends Dispatcher {
    private static String path = "D:\\softwareDev\\iDebug\\src\\main\\resources\\com\\iDebug\\pickloose\\music\\correct.wav";

    @Override
    void dispatch(Response response) {
        JsonObject body = new Gson().fromJson(response.getBody(), JsonObject. class);
        String userId = body.get("player").getAsString();
        int points = body.get("points").getAsInt();
        Platform.runLater(()->{GameManager.getInstance().updatePlayerGUIPoints(userId, points);});
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }
}
