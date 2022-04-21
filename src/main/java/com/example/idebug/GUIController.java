package com.example.idebug;

import com.example.idebug.network.Response;

public class GUIController {
    private static GUIController guiController;
    private GUIController(){

    }
    public static GUIController getInstance() {
        if(guiController == null)
            guiController = new GUIController();
        return guiController;
    }
    public void handle(Response response) {
        switch (response.getService()) {
            case NEW_MESSAGE -> FXMessageController.guiNewMsg(Message.serialize(response.getBody()),
                    OBSERVER.RECEIVER);
        }
    }
}
