package com.iDebug.pickloose;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LobbyManager {

    class GUIPlayer {
        private AuthUser player;
        private HBox playerCard;
        private ImageView status;

        private HBox getGUI() throws IOException {
            playerCard = new HBox();
            playerCard.getStyleClass().add("card");
            playerCard.setMinHeight(40);
            playerCard.setSpacing(5);

            HBox avatar = new HBox();
            avatar.setAlignment(Pos.CENTER);
            ImageView image = new ImageView();
            // map avatar code to avatar image src
            image.setImage(new Image("file:"+player.getAvatarSrc()));
            avatar.getChildren().add(image);
            image.setFitHeight(37);
            image.setFitWidth(40);

            HBox userName = new HBox();
            userName.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(userName, Priority.ALWAYS);
            Label label = new Label(player.getUsername());
            label.getStyleClass().add("option-text");
            userName.getChildren().add(label);

            HBox statusContainer = new HBox();
            statusContainer.setAlignment(Pos.CENTER);
            HBox.setHgrow(statusContainer, Priority.NEVER);
            status = new ImageView();
            status.setImage(new Image("file:src/main/resources/com/iDebug/pickloose/widgets/not-ready.png"));
            status.setFitWidth(25);
            status.setFitHeight(25);
            statusContainer.getChildren().add(status);

            playerCard.getChildren().add(avatar);
            playerCard.getChildren().add(userName);
            playerCard.getChildren().add(statusContainer);

            return playerCard;
        }
        public void setReady() {
            status.setImage(new Image("file:src/main/resources/com/iDebug/pickloose/widgets/ready.png"));
        }
        public void setNotReady() {
            status.setImage(new Image("file:src/main/resources/com/iDebug/pickloose/widgets/not-ready.png"));
        }
        GUIPlayer(AuthUser player) {
            this.player = player;
            try {
                container.getChildren().add(getGUI());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void setHost() {
            status.setImage(new Image("file:src/main/resources/com/iDebug/pickloose/widgets/host.png"));
        }
    }

    private static LobbyManager lobbyManager;
    private VBox container;
    private HashMap<String, LobbyManager.GUIPlayer> GUIPlayers;
    private GUIGameSettings guiGameSettings;

    private LobbyManager() {
        GUIPlayers = new HashMap<>();
    }
    public static LobbyManager getInstance() {
        if(lobbyManager == null)
            lobbyManager = new LobbyManager();
        return lobbyManager;
    }

    public VBox getContainer() {
        return container;
    }

    public void setContainer(VBox container) {
        this.container = container;
    }

    public void addPlayer(AuthUser authUser) {
        GUIPlayers.put(authUser.getUserid(), new GUIPlayer(authUser));
    }

    public void removePlayer(AuthUser user) {
        GUIPlayer guiPlayer = GUIPlayers.get(user.getUserid());
        container.getChildren().remove(guiPlayer.playerCard);
    }

    public void setGuiGameSettings(GUIGameSettings guiGameSettings) {
        this.guiGameSettings = guiGameSettings;
    }

    public void setGuiGameSettings(GameSettings gameSettings) {
        guiGameSettings.set(gameSettings);
    }

    public void setReady(AuthUser user) {
        GUIPlayers.get(user.getUserid()).setReady();
    }

    public void setReady(String userid) {
        GUIPlayers.get(userid).setReady();
    }

    public void setNotReady(AuthUser user) {
        GUIPlayers.get(user.getUserid()).setNotReady();
    }

    public void setHost(AuthUser user) { GUIPlayers.get(user.getUserid()).setHost();}
}
