package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.network.Response;
import javafx.application.Platform;


public class ClientDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) throws Exception {
        new Thread(() -> {
            try {
                switch (response.getService()) {
                    case GET_AUTH -> Platform.runLater(() -> {
                        new GetAuthDispatcher().dispatch(response);
                    });
                    case ADD_USER -> Platform.runLater(() -> {
                        new AddUserDispatcher().dispatch(response);
                    });
                    case DELETE_USER -> Platform.runLater(() -> {
                        new RemoveUserDispatcher().dispatch(response);
                    });
                    case SET_GAME_SETTINGS -> Platform.runLater(() -> {
                        new SetGameSettingsDispatcher().dispatch(response);
                    });
                    case GET_GAME_SETTINGS -> Platform.runLater(() -> {
                        new GetGameSettingsDispatcher().dispatch(response);
                    });
                    case SET_READY -> Platform.runLater(() -> {
                        new SetReadyDispatcher().dispatch(response);
                    });
                    case SET_NOT_READY -> Platform.runLater(() -> {
                        new SetNotReadyDispatcher().dispatch(response);
                    });
                    case START_GAME -> Platform.runLater(() -> {
                        new StartGameDispatcher().dispatch(response);
                    });
                    case NEW_MESSAGE -> Platform.runLater(() -> {
                        new NewMessageDispatcher().dispatch(response);
                    });
                    case UPDATE_CANVAS -> Platform.runLater(() -> {
                        new UpdateCanvasDispatcher().dispatch(response);
                    });
                    case GET_CANVAS_SOCKET -> Platform.runLater(() -> {
                        new CanvasSocketDispatcher().dispatch(response);
                    });
                    case GET_ALL_MEMBER -> Platform.runLater(() -> {
                        new GetAllMemberDispatcher().dispatch(response);
                    });
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
