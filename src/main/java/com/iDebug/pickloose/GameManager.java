package com.iDebug.pickloose;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.iDebug.pickloose.animation.FadeIn;
import com.iDebug.pickloose.network.SERVICE;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.*;

public class GameManager {
    private static GameManager gameManager;
    private final HashMap<String, GUIPlayerCard> useridGUIMapping;
    // todo : use database for words
    String[] words = {"Angel", "Eyeball", "Pizza", "Angry", "Fireworks", "Pumpkin", "Baby", "Flower", "Rainbow",
            "Beard", "Recycle", "Bible", "Giraffe", "Castle", "Glasses", "Snowflake", "Book", "Heel", "Stairs",
            "Bucket", "Cream", "Starfish", "bee", "Igloo", "Strawberry", "Butterfly", "Lady", "Sun", "Camera", "Lamp",
            "Tire", "Cat", "Lion", "Toast", "Church", "Mailbox", "Toothbrush", "Crayon", "Night", "Toothpaste",
            "Dolphin", "Nose", "Truck", "Egg", "Olympics", "Volleyball", "Tower", "Peanut"};
    private USER_MODE userMode;
    private VBox playerContainer;
    private GameSettings gameSettings;
    private Label guiTimer;
    private String currentPainter;
    private String selectedWord;
    private final Set<String> validGuesses;
    private final HashMap<String, Integer> pointTable;

    private GameManager() {
        useridGUIMapping = new HashMap<>();
        validGuesses = new HashSet<>();
        pointTable = new HashMap<>();
        resetPointTable();
    }

    public static GameManager getInstance() {
        if (gameManager == null)
            gameManager = new GameManager();
        return gameManager;
    }

    private void resetPointTable() {
        pointTable.clear();
    }

    public USER_MODE getUserMode() {
        return userMode;
    }

    public void setUserMode(USER_MODE mode) {
        userMode = mode;
    }

    public void setPlayerContainer(VBox playerContainer) {
        this.playerContainer = playerContainer;
    }

    public void addPlayer(AuthUser authUser) {
        useridGUIMapping.put(authUser.getUserid(), new GUIPlayerCard(authUser));
    }

    public void removePlayer(AuthUser authUser) {
        GUIPlayerCard guiPlayerCard = useridGUIMapping.get(authUser.getUserid());
        playerContainer.getChildren().remove(guiPlayerCard);
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public void startGame() throws SQLException, InterruptedException { // only host should use this function
        int rounds = Integer.parseInt(gameSettings.getRounds());
        int duration = Integer.parseInt(gameSettings.getRoundDuration());
        var players = UserManager.getInstance().getPlayers();
        Collections.shuffle(players);

        for (int i = 0; i < rounds; i++) {
            for (int j = 0; j < players.size(); j++) {
                // play
                AuthUser player = players.get(j);
                currentPainter = player.getUserid();
                selectedWord = words[(int) (Math.random() * (words.length))];
                JsonObject msg = new JsonObject();
                msg.addProperty("player", AuthUser.deserialize(player));
                msg.addProperty("word", selectedWord);
                NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.PLAY_MATCH, msg.toString());

                Thread.sleep(3000);

                ServerTimer timer = new ServerTimer(duration);
                timer.start();
                timer.join();
                //result
                Gson gson = new GsonBuilder().create();
                String json = gson.toJson(pointTable);
                NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.ROUND_RESULT, json);
                // reset
                validGuesses.clear();
                NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.CLEAR_CANVAS);
                resetPointTable();

                Thread.sleep(3000);
            }
        }

    }

    public String getSelectedWord() {
        return selectedWord;
    }

    public void updatePlayerGUIPoints(String userId, int points) {
        GUIPlayerCard guiPlayerCard = useridGUIMapping.get(userId);
        guiPlayerCard.updatePoints(points);
    }

    public boolean hasGuessed(String userId) {
        return validGuesses.contains(userId);
    }

    public int getValidGuessAmount() {
        return validGuesses.size();
    }

    public void addGuess(String userId, int points) {
        validGuesses.add(userId);
        pointTable.put(userId, points);
    }

    class GUIPlayerCard {
        private final AuthUser authUser;
        Label guiPoints;
        private HBox playerCard;

        GUIPlayerCard(AuthUser authUser) {
            this.authUser = authUser;
            playerContainer.getChildren().add(getGUI());
        }

        public void updatePoints(int points) {
            guiPoints.setText(points + " Points");
            new FadeIn(playerCard).play();
        }

        private HBox getGUI() {
            playerCard = new HBox();
            playerCard.getStyleClass().add("player");
            playerCard.setMaxHeight(50);

            HBox playerAvatar = new HBox();
            playerAvatar.setAlignment(Pos.CENTER);
            ImageView avatarImage = new ImageView();
            avatarImage.setFitHeight(35);
            avatarImage.setFitWidth(38);
            avatarImage.setImage(new Image("file:" + authUser.getAvatarSrc()));
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
            guiPoints = new Label("0" + " Points");
            guiPoints.getStyleClass().add("points");
            pointsContainer.getChildren().add(guiPoints);
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

            playerCard.getChildren().addAll(playerAvatar, playerInfo, playerStatus);
            return playerCard;
        }

    }
}
