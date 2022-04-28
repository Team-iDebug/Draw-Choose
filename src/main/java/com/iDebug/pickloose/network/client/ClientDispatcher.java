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
                    case NEW_MESSAGE -> Platform.runLater(() -> {
                        new NewMessageDispatcher().dispatch(response);
                    });
                    case UPDATE_CANVAS -> Platform.runLater(() -> {
                        new UpdateCanvasDispatcher().dispatch(response);
                    });
                    case GET_CANVAS_SOCKET -> Platform.runLater(() -> {
                        new CanvasSocketDispatcher().dispatch(response);
                    });
                    case GET_ALL_MEMEBER -> Platform.runLater(() -> {
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
