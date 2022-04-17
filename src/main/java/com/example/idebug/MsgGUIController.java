package com.example.idebug;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class MsgGUIController {
    private HashMap<String, Message> messages; // maps unique message id to Message object
    private static MsgGUIController msgGUIController;

    private MsgGUIController() {

    }

    public static MsgGUIController getInstance() {
        if(msgGUIController == null)
            msgGUIController = new MsgGUIController();
        return msgGUIController;
    }

    public void send(VBox msgContainer, Message message) {
        HBox parentBox = new HBox();
        parentBox.getStyleClass().add("message-box-received-container");

        VBox msgBox = new VBox();
        msgBox.getStyleClass().add("message-box-test");

        HBox senderBox = new HBox();
        Label sender = new Label(message.getUserid());
        sender.getStyleClass().add("message-sender");
        senderBox.getChildren().add(sender);

        HBox textBox = new HBox();
        Label text = new Label(message.getText());
        text.setWrapText(true);
        text.getStyleClass().add("message-text");
        textBox.getChildren().add(text);

        HBox infoBox = new HBox();
        HBox timeBox = new HBox();
        HBox statusBox = new HBox();
        Label time = new Label(message.getTime().getMinute() + ":" + message.getTime().getSecond());
        Label status = new Label(message.getStatus().toString());
        time.getStyleClass().add("message-info");
        time.getStyleClass().add("message-info-time");
        HBox.setHgrow(time, Priority.ALWAYS);
        status.getStyleClass().add("message-info");
        status.getStyleClass().add("message-info-status");
        HBox.setHgrow(status,Priority.ALWAYS);
        timeBox.getChildren().add(time);
        statusBox.getChildren().add(status);
        infoBox.getChildren().add(statusBox);
        infoBox.getChildren().add(timeBox);

        msgBox.getChildren().add(senderBox);
        msgBox.getChildren().add(textBox);
        msgBox.getChildren().add(infoBox);

        parentBox.getChildren().add(msgBox);

        msgContainer.getChildren().add(parentBox);
    }
}
