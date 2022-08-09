package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.network.Request;

import java.io.IOException;
import java.net.Socket;

public class ServerDispatcher extends Thread {
    Socket socket;
    String msg;

    ServerDispatcher(Socket socket, String req) throws IOException {
        this.socket = socket;
        this.msg = req;
    }

    public void dispatch(Request req) {
        try {
            switch (req.getService()) {
                case GET_AUTH -> new GetAuthDispatcher().dispatch(req, socket);
                case ADD_USER -> new AddUserDispatcher().dispatch(req,socket);
                case DELETE_USER -> new RemoveUserDispatcher().dispatch(req,socket);
                case MAKE_HOST -> new MakeHostDispatcher().dispatch(req,socket);
                case SET_GAME_SETTINGS -> new SetGameSettingsDispatcher().dispatch(req,socket);
                case GET_GAME_SETTINGS -> new GetGameSettingsDispatcher().dispatch(req,socket);
                case SET_READY -> new SetReadyDispatcher().dispatch(req,socket);
                case SET_NOT_READY -> new SetNotReadyDispatcher().dispatch(req,socket);
                case START_GAME -> new StartGameDispatcher().dispatch(req,socket);
                case NEW_MESSAGE -> new NewMessageDispatcher().dispatch(req, socket);
                case GET_CANVAS_SOCKET -> new CanvasSocketDispatcher().dispatch(req,socket);
                case CLEAR_CANVAS -> new ClearCanvasDispatcher().dispatch(req,socket);
                case GET_ALL_MEMBER -> new GetAllMemberDispatcher().dispatch(req,socket);
                case SET_TOOL -> new SetToolDispatcher().dispatch(req,socket);
                case SET_COLOR -> new SetColorDispatcher().dispatch(req,socket);
                case PLAY_MATCH -> new StartMatchDispatcher().dispatch(req,socket);
                case STOP_MATCH -> new StopMatchDispatcher().dispatch(req,socket);
                case UPDATE_TIMER -> new TimerDispatcher().dispatch(req,socket);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        dispatch(Request.serialize(msg));
    }
}
