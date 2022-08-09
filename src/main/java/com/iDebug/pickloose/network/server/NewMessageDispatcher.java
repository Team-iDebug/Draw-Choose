package com.iDebug.pickloose.network.server;

import com.google.gson.JsonObject;
import com.iDebug.pickloose.*;
import com.iDebug.pickloose.database.server.ServerDatabase;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.Response;
import com.iDebug.pickloose.network.SERVICE;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

class NewMessageDispatcher extends Dispatcher {
    @Override
    void dispatch(Request request, Socket socket) throws IOException {
        Message msg = Message.serialize(request.getBody());
        String userId = request.getAuthSender().getUserid();
        String selectedWord = GameManager.getInstance().getSelectedWord();
        Response response = null;
        if(msg.getText().equalsIgnoreCase(selectedWord)) { // increase points, announce this
            // increase point
            // database part

            if(!GameManager.getInstance().hasGuessed(userId)) {
                int points = 0, prevPoints = 0, newPoints = 0;
                try {
                    prevPoints = Manager.getInstance().getPoints(userId);
                    // todo : point system
                    int duration = Integer.parseInt(GameManager.getInstance().getGameSettings().getRoundDuration());
                    int timeLeft = duration - ServerTimer.getTime();
                    newPoints = (int) ((double)(duration*10.0)/(double)timeLeft);
                    points = prevPoints + newPoints;
                    ServerDatabase.getInstance().updatePoints(request.getAuthSender().getUserid(),points);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                // gui part
                JsonObject res = new JsonObject();
                res.addProperty("player",userId);
                res.addProperty("points",points);
                broadcastRespond(new Response(SERVICE.UPDATE_POINTS,FEEDBACK.SUCCEED,res.toString()));
                GameManager.getInstance().addGuess(userId,newPoints);
            }

            // announcement
            String text = request.getSender().getUsername() + " guessed the word.";
            Message responseMsg = new Message("SERVER",msg.getTime(),msg.getType(),text, MSG_STATUS.SENT);
            response = new Response(request.getService(), FEEDBACK.SUCCEED,Message.deSerialize(responseMsg));
            broadcastExcludeRespond(response,socket);

            text = "You guessed the word.";
            responseMsg = new Message("SERVER",msg.getTime(),msg.getType(),text, MSG_STATUS.SENT);
            response = new Response(request.getService(), FEEDBACK.SUCCEED,Message.deSerialize(responseMsg));
            individualRespond(response,socket);
        }
        else {
            response = new Response(request.getService(), FEEDBACK.SUCCEED,request.getBody());
            broadcastExcludeRespond(response,socket);
        }
    }
}
