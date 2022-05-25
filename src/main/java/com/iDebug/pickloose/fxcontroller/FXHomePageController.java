package com.iDebug.pickloose.fxcontroller;

import com.iDebug.pickloose.*;
import com.iDebug.pickloose.database.client.ClientDatabase;
import com.iDebug.pickloose.database.server.ServerDatabase;
import com.iDebug.pickloose.network.SERVICE;
import com.iDebug.pickloose.network.client.Client;
import com.iDebug.pickloose.network.client.GameClientListener;
import com.iDebug.pickloose.network.server.GameServerListener;
import com.iDebug.pickloose.network.server.Server;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
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
            Client client = new Client(server,port, GameClientListener.class);
            NetworkManager.getInstance().setGameClient(client);
            client.start();
            Platform.runLater(() -> {
                NetworkManager.getInstance().sendReqAsUser(SERVICE.GET_AUTH, User.deserialize(NetworkManager.getInstance().getUser()));
                WindowManager.getInstance().setScene(SCENES.LOBBY);
            });
        }
        catch (Exception e) {
            Platform.runLater(() -> showError("Could not connect to the server."));
        }
    }

    @FXML
    public void initialize() {
        // setting random client id for database
        ClientDatabase.setUrl(String.valueOf(Math.round(Math.random()*10000)));

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
                NetworkManager.getInstance().setUser(new User(getUserName(),getSelectedAvatar()));
                String text = joinGameDialogueBox();
                if(validateServerAddress(text)) {
                    GameManager.getInstance().setUserMode(USER_MODE.NORMAL);
                    ArrayList<String> parsedString = new ArrayList<String>(Arrays.asList(text.split(":")));
                    String serverIP = parsedString.get(0);
                    int port = Integer.parseInt(parsedString.get(1));
                    new Thread(() -> sendJoinGameRequest(serverIP,port)).start();
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
                try {
                    ServerDatabase.getInstance().clear();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
                NetworkManager.getInstance().setUser(new User(getUserName(),getSelectedAvatar()));
                GameManager.getInstance().setUserMode(USER_MODE.HOST);
                Server server = new Server(0, GameServerListener.class);
                server.start();
//                String serverIP = server.getSocket().getInetAddress().getHostAddress();
                int port = server.getSocket().getLocalPort();
                try {
                    String serverIP = InetAddress.getLocalHost().getHostAddress();
                    new Thread(()-> sendJoinGameRequest(serverIP,port)).start();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.MAKE_HOST);
                });
            }
        });
    }
}
