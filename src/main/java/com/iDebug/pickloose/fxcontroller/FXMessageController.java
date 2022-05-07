package com.iDebug.pickloose.fxcontroller;

import com.iDebug.pickloose.*;
import com.iDebug.pickloose.network.SERVICE;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private HBox FXGif1,FXGif2,FXGif3,FXGif4,FXGif5;

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
        Message msg = new Message(NetworkManager.getInstance().getUser().getUsername(),
                LocalTime.now().toString(), MSG_TYPE.PLAIN_TEXT, msgText, MSG_STATUS.SENT);
        guiNewMsg(msg,OBSERVER.SENDER);
        NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.NEW_MESSAGE,Message.deSerialize(msg));
    }

    private void sendNewGif(String gif) {
        Message msg = new Message(NetworkManager.getInstance().getUser().getUsername(),
                LocalTime.now().toString(), MSG_TYPE.GIF, gif, MSG_STATUS.SENT);
        guiNewMsg(msg,OBSERVER.SENDER);
        NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.NEW_MESSAGE,Message.deSerialize(msg));
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

        HBox[] gifs = {FXGif1,FXGif2,FXGif3,FXGif4,FXGif5};
        for (var gif : gifs) {
            gif.setOnMousePressed(e -> {
                sendNewGif(gif.getId());
            });
        }
    }

    private static VBox getGuiMessageBox(Message message) throws NullPointerException {
        VBox msgBox = new VBox();
        if(message.getType() == MSG_TYPE.PLAIN_TEXT)
            msgBox.getStyleClass().add("message-box-dark");
        else if(message.getType() == MSG_TYPE.GIF)
            msgBox.getStyleClass().add("message-box-light");

        HBox senderBox = new HBox();
        Label sender = new Label(message.getUserid());
        if(message.getType() == MSG_TYPE.PLAIN_TEXT)
            sender.getStyleClass().add("message-sender-dark");
        else if(message.getType() == MSG_TYPE.GIF)
            sender.getStyleClass().add("message-sender-light");
        senderBox.getChildren().add(sender);

        HBox contentBox = new HBox();
        if(message.getType() == MSG_TYPE.PLAIN_TEXT) {
            Label text = new Label(message.getText());
            text.setWrapText(true);
            text.getStyleClass().add("message-text");
            contentBox.getChildren().add(text);
        }
        else if(message.getType() == MSG_TYPE.GIF) {
            ImageView image = new ImageView();
            image.setImage(new Image(GifFactory.getInstance().getGif(message.getText())));
            image.setFitWidth(150);
            image.setFitHeight(150);
            contentBox.getChildren().add(image);
        }

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
        msgBox.getChildren().add(contentBox);
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