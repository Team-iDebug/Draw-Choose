package com.iDebug.pickloose.network.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;

import java.io.IOException;
import java.net.Socket;

public class StartMatchDispatcher extends Dispatcher {
    @Override
    protected void dispatch(Request request, Socket socket) throws IOException {
        JsonObject msg = new Gson().fromJson(request.getBody(), JsonObject. class);
        AuthUser player = AuthUser.serialize(msg.get("player").getAsString());
        String word = msg.get("word").getAsString();
        Socket playerSocket = Manager.getInstance().getSocket(player.getUserid());

        JsonObject drawResponse = new JsonObject();
        drawResponse.addProperty("player",player.getUsername());
        drawResponse.addProperty("mode","draw");
        drawResponse.addProperty("word", word);

        JsonObject guessResponse = new JsonObject();
        guessResponse.addProperty("player",player.getUsername());
        guessResponse.addProperty("mode","guess");
        guessResponse.addProperty("length",String.valueOf(word.length()));

        individualRespond(new Response(request.getService(), FEEDBACK.SUCCEED, drawResponse.toString()), playerSocket);
        broadcastExcludeRespond(new Response(request.getService(), FEEDBACK.SUCCEED, guessResponse.toString()),
                playerSocket);
    }
}
