package com.example.idebug;

import com.example.idebug.network.SERVICE;
import com.example.idebug.network.client.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FXHomePageController {
    @FXML
    private TextField userName;
    @FXML
    private ImageView avatar1;
    @FXML
    private ImageView avatar2;
    @FXML
    private ImageView avatar3;
    @FXML
    private ImageView avatar4;
    @FXML
    private ImageView avatar5;
    @FXML
    private ImageView avatar6;
    @FXML
    private ImageView avatar7;
    @FXML
    private ImageView avatar8;

    @FXML
    private Button joinButton;
    @FXML
    private Button hostButton;
    @FXML
    private HBox messageBox;

    private static String selectedAvatar = "avatar1";

    private String getUserName() {
        return userName.getText();
    }

    private String getSelectedAvatar() {
        return selectedAvatar;
    }

    private boolean userNameGiven() {
        String name = getUserName();
        return (name != null && name.length() != 0);
    }

    private void showError(String error) {
        messageBox.getChildren().clear();
        Label label = new Label(error);
        label.getStyleClass().add("error-txt");
        messageBox.getChildren().add(label);
        messageBox.setVisible(true);
    }

    private boolean validateInputs() {
        if(!userNameGiven()) {
            showError("Invalid Username or Avatar");
            return false;
        }
        return true;
    }

    private boolean validateServerAddress(String serverAddress) {
        Pattern pattern = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]):[0-9]+$");
        Matcher matcher = pattern.matcher(serverAddress);
        return (matcher.find());
    }

    private String joinGameDialogueBox() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Server Address");
        dialog.setTitle("Join Game");
        dialog.setHeaderText("Ask the host for server address.");
        TextField textField = dialog.getEditor();
        dialog.showAndWait();
        String text = textField.getText();
        return text;
    }

    private void sendJoinGameRequest(String server, int port) {
        try {
            Client client = new Client(server,port);
            Player.getInstance().setClient(client);
            client.start();
            Platform.runLater(() -> {
                Player.sendReqAsUser(SERVICE.ADD_USER,User.deserialize(Player.getInstance().getUser()));
                WindowManager.getWindowManager().setScene(SCENES.GAME);
            });
        }
        catch (Exception e) {
            Platform.runLater(() -> showError("Could not connect to the server."));
        }
    }

    @FXML
    public void initialize() {
        ImageView avatars[] = {
                avatar1,avatar2,avatar3,avatar4,avatar5,avatar6,avatar7,avatar8
        };
        for(var avatar : avatars) {
            avatar.setOnMouseClicked(mouseEvent -> {
                selectedAvatar = avatar.getId();
            });
        }
        userName.setOnKeyPressed(keyEvent -> {
            messageBox.setVisible(false);
        });
        joinButton.setOnMouseClicked(mouseEvent -> {
            messageBox.setVisible(false);
            if(validateInputs()) {
                Player.getInstance().setUser(new User(getUserName(),getSelectedAvatar()));
                String text = joinGameDialogueBox();
                if(validateServerAddress(text)) {
                    ArrayList<String> parsedString = new ArrayList<String>(Arrays.asList(text.split(":")));
                    String server = parsedString.get(0);
                    int port = Integer.parseInt(parsedString.get(1));
                    new Thread(() -> sendJoinGameRequest(server,port)).start();
                    System.out.println("Connecting...");
                }
                else {
                    showError("Invalid Server Address");
                }
            }
        });
        hostButton.setOnMouseClicked(mouseEvent -> {
            messageBox.setVisible(false);
            if(validateInputs()) {

            }
        });
    }
}
