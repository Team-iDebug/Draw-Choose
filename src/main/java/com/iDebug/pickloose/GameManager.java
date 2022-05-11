package com.iDebug.pickloose;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class GameManager {
    class GUIPlayerCard {
        private AuthUser authUser;
        private HBox playerCard;
        private HBox getGUI() {
            playerCard = new HBox();
            playerCard.getStyleClass().add("player");
            playerCard.setMaxHeight(50);

            HBox playerAvatar = new HBox();
            playerAvatar.setAlignment(Pos.CENTER);
            ImageView avatarImage = new ImageView();
            avatarImage.setFitHeight(35);
            avatarImage.setFitWidth(38);
            avatarImage.setImage(new Image("file:"+authUser.getAvatarSrc()));
            playerAvatar.getChildren().add(avatarImage);

            HBox playerInfo = new HBox();
            HBox.setHgrow(playerInfo, Priority.ALWAYS);
            VBox vBox = new VBox();
            HBox nameContainer = new HBox();
            nameContainer.setAlignment(Pos.BOTTOM_LEFT);
            Label name = new Label(authUser.getUsername());
            name.getStyleClass().add("player-name");
            nameContainer.getChildren().add(name);
            HBox pointsContainer = new HBox();
            pointsContainer.setAlignment(Pos.TOP_LEFT);
            Label points = new Label("6969" + " Points");
            points.getStyleClass().add("points");
            pointsContainer.getChildren().add(points);
            vBox.getChildren().add(nameContainer);
            vBox.getChildren().add(pointsContainer);
            playerInfo.getChildren().add(vBox);

            HBox playerStatus = new HBox();
            playerStatus.setAlignment(Pos.CENTER);
            HBox hBox = new HBox();
            hBox.getStyleClass().add("player-status");
            hBox.setMinWidth(20);
            hBox.setMaxWidth(20);
            hBox.setMinHeight(20);
            hBox.setMaxHeight(20);
            ImageView status = new ImageView();
            status.setFitHeight(12);
            status.setFitWidth(12);
            status.setImage(new Image("file:src\\main\\resources\\com\\iDebug\\pickloose\\widgets\\yellow-brush.png"));
            hBox.getChildren().add(status);
            playerStatus.getChildren().add(hBox);

            playerCard.getChildren().addAll(playerAvatar,playerInfo,playerStatus);
            return playerCard;
        }
        GUIPlayerCard(AuthUser authUser) {
            this.authUser = authUser;
            playerContainer.getChildren().add(getGUI());
        }
    }
    private static GameManager gameManager;
    private USER_MODE userMode;
    private VBox playerContainer;
    private HashMap<String,GUIPlayerCard> useridGUIMapping;

    private GameManager() {
        useridGUIMapping = new HashMap<>();
    }
    public static GameManager getInstance() {
        if(gameManager == null)
            gameManager = new GameManager();
        return gameManager;
    }
    public void setUserMode(USER_MODE mode) {
        userMode = mode;
    }
    public USER_MODE getUserMode() {
        return userMode;
    }
    public void setPlayerContainer(VBox playerContainer) {
        this.playerContainer = playerContainer;
    }
    public void addPlayer(AuthUser authUser) {
        useridGUIMapping.put(authUser.getUserid(),new GUIPlayerCard(authUser));
    }
    public void removePlayer(AuthUser authUser) {
        GUIPlayerCard guiPlayerCard = useridGUIMapping.get(authUser.getUserid());
        playerContainer.getChildren().remove(guiPlayerCard);
    }
}
