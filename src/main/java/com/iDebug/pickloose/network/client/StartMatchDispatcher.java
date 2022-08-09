package com.iDebug.pickloose.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iDebug.pickloose.WindowManager;
import com.iDebug.pickloose.canvas.DrawManager;
import com.iDebug.pickloose.fxcontroller.FXGameWordController;
import com.iDebug.pickloose.fxcontroller.FXMessageController;
import com.iDebug.pickloose.network.Response;

public class StartMatchDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        JsonObject body = new Gson().fromJson(response.getBody(), JsonObject.class);
        String player = body.get("player").getAsString();

        // slide show
        WindowManager.getInstance().announcePlayer(player);

        if(body.get("mode").getAsString().equals("draw")) {
            // show the word
            String word = body.get("word").getAsString();
            FXGameWordController.setWord(word);
            // active drawing mode && inactive guessing mode
            DrawManager.getInstance().setMode(DrawManager.MODE.DRAW);
            FXMessageController.setMode(FXMessageController.MODE.VIEW);
        }
        else if(body.get("mode").getAsString().equals("guess")){
            // show the word length
            int length = body.get("length").getAsInt();
            FXGameWordController.setAnonymousWord(length);
            // active guessing mode && inactive drawing mode
            DrawManager.getInstance().setMode(DrawManager.MODE.VIEW);
            FXMessageController.setMode(FXMessageController.MODE.TYPE);
        }
    }
}
