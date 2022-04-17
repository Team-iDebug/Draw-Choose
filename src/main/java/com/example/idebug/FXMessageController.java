package com.example.idebug;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalTime;

public class FXMessageController {
    @FXML
    private TextField msgTextField;

    @FXML
    private HBox msgSendButton;

    @FXML
    private VBox msgContainer;

    @FXML
    private ScrollPane scrollPane;

    private void sendMsg(String text) {
        if(text.equals(""))
            return;
        MsgGUIController.getInstance().send(msgContainer,new Message("Nayem", LocalTime.now(),text, Message.NetworkStatus.SENT));
        msgTextField.setText("");
        scrollPane.setVvalue(1.0);
    }

    @FXML
    private void onMsgSendButton() {
        sendMsg(msgTextField.getText());
    }

    @FXML
    public void initialize() {
        msgTextField.setOnKeyPressed(event -> {
            if( event.getCode() == KeyCode.ENTER ) {
                sendMsg(msgTextField.getText());
            }
        } );
    }
}