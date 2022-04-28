package com.iDebug.pickloose;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


import java.util.HashMap;

public class LobbyManager {
    class GUIPlayer {
        private AuthUser player;
        private HBox playerCard;
        private ImageView status;

        public HBox getGUI() {
            playerCard = new HBox();
            playerCard.getStyleClass().add("card");
            playerCard.setMinHeight(40);
            playerCard.setSpacing(5);

            HBox avatar = new HBox();
            avatar.setAlignment(Pos.CENTER);
            ImageView image = new ImageView();
            // map avatar code to avatar image src
            image.setImage(new Image(player.getAvatar()));
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
            status.setImage(new Image("D:\\softwareDev\\iDebug\\src\\main\\resources\\com\\iDebug\\pickloose\\widgets\\ready.png"));
            status.setFitWidth(25);
            status.setFitHeight(25);
            statusContainer.getChildren().add(status);

            playerCard.getChildren().add(avatar);
            playerCard.getChildren().add(userName);
            playerCard.getChildren().add(statusContainer);

            return playerCard;
        }
        GUIPlayer(AuthUser player) {
            this.player = player;
            container.getChildren().add(getGUI());
        }
    }

    private static LobbyManager lobbyManager;
    private VBox container;
    private HashMap<String, LobbyManager.GUIPlayer> GUIPlayers;

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

    public void addPlayer(AuthUser player) {
        GUIPlayers.put(player.token, new GUIPlayer(player));
    }
}
