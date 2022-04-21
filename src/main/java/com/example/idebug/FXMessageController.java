package com.example.idebug;

import com.example.idebug.network.SERVICE;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalTime;

public class FXMessageController {

    @FXML
    private TextField msgTextField;
    private static TextField fxmlMsgField;

    @FXML
    private HBox msgSendButton;
    private static HBox fxmlMsgSendButton;

    @FXML
    private VBox msgContainer;
    private static VBox fxmlMsgContainer;

    @FXML
    private ScrollPane scrollPane;
    private static ScrollPane fxmlScrollPane;

    public static void guiNewMsg(Message message, OBSERVER observer) {
        if(observer == OBSERVER.SENDER)
            send(fxmlMsgContainer,message);
        if(observer == OBSERVER.RECEIVER)
            receive(fxmlMsgContainer,message);
        fxmlMsgField.setText("");
        fxmlScrollPane.setVvalue(1.0);
    }

    private void sendNewMessage() {
        String msgText = msgTextField.getText();
        Message msg = new Message(Player.getInstance().getUser().getUsername(),
                LocalTime.now().toString(),msgText, Message.NetworkStatus.SENT);
        guiNewMsg(msg,OBSERVER.SENDER);
        Player.sendReqAsAuthUser(SERVICE.NEW_MESSAGE,Message.deSerialize(msg));
    }

    @FXML
    private void onMsgSendButton() {
        sendNewMessage();
    }

    @FXML
    public void initialize() {
        fxmlMsgContainer = msgContainer;
        fxmlMsgField = msgTextField;
        fxmlScrollPane = scrollPane;
        fxmlMsgSendButton = msgSendButton;

        msgTextField.setOnKeyPressed(event -> {
            if( event.getCode() == KeyCode.ENTER ) {
                sendNewMessage();
            }
        } );
    }

    private static VBox getGuiMessageBox(Message message) throws NullPointerException {
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
        LocalTime t = LocalTime.parse(message.getTime());
        Label time = new Label(t.getMinute() + ":" + t.getSecond());
        Label status = new Label(message.getStatus().toString());
        time.getStyleClass().add("message-info");
        HBox.setHgrow(timeBox, Priority.ALWAYS);
        status.getStyleClass().add("message-info");
        HBox.setHgrow(statusBox,Priority.ALWAYS);
        timeBox.getChildren().add(time);
        statusBox.getChildren().add(status);
        statusBox.setAlignment(Pos.BOTTOM_LEFT);
        timeBox.setAlignment(Pos.BOTTOM_RIGHT);
        infoBox.getChildren().add(statusBox);
        infoBox.getChildren().add(timeBox);

        msgBox.getChildren().add(senderBox);
        msgBox.getChildren().add(textBox);
        msgBox.getChildren().add(infoBox);

        return msgBox;
    }

    public static void send(VBox msgContainer, Message message) {
        HBox parentBox = new HBox();
        parentBox.getStyleClass().add("message-box-sent-container");

        VBox msgBox = getGuiMessageBox(message);

        parentBox.getChildren().add(msgBox);
        msgContainer.getChildren().add(parentBox);
    }

    public static void receive(VBox msgContainer, Message message) {
        HBox parentBox = new HBox();
        parentBox.getStyleClass().add("message-box-received-container");

        VBox msgBox = getGuiMessageBox(message);

        parentBox.getChildren().add(msgBox);
        msgContainer.getChildren().add(parentBox);
    }
}