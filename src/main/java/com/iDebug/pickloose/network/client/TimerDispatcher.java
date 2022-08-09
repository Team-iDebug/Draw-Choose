package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.fxcontroller.FXCanvasController;
import com.iDebug.pickloose.network.Response;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;


public class TimerDispatcher extends Dispatcher {
    private static final String path = "D:\\softwareDev\\iDebug\\src\\main\\resources\\com\\iDebug\\pickloose\\music\\countdown.wav";

    @Override
    void dispatch(Response response) {
        Label guiTimer = FXCanvasController.getGuiTimer();
        Platform.runLater(() -> {
            guiTimer.setText(response.getBody());
        });
//        Media media = new Media(new File(path).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setCycleCount(1);
//        mediaPlayer.play();
    }
}
