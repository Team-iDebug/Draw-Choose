package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.UserManager;
import com.iDebug.pickloose.network.Response;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

class AddUserDispatcher extends Dispatcher {
    private final static String path = "src\\main\\resources\\com\\iDebug\\pickloose\\music\\kop.wav";

    @Override
    void dispatch(Response response) {
        try {
            AuthUser user = new Gson().fromJson(response.getBody(),AuthUser.class);
            UserManager.getInstance().add(user);
            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(1);
            mediaPlayer.play();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
