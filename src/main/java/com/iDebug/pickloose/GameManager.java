package com.iDebug.pickloose;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.*;

public class GameManager {
    private static GameManager gameManager;
    private final HashMap<String, GUIPlayerCard> useridGUIMapping;
    private USER_MODE userMode;
    private VBox playerContainer;
    private GameSettings gameSettings;
    private Label guiTimer;
    private boolean selected;
    private int totalValidSubmission;

    private GameManager() {
        useridGUIMapping = new HashMap<>();
    }

    public static GameManager getInstance() {
        if (gameManager == null)
            gameManager = new GameManager();
        return gameManager;
    }

    public void setGUITimer(Label timer) {
        guiTimer = timer;
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

    private void resetPlayerSession() {
        totalValidSubmission = 0;
        selected = false;
    }

    public void startGame() throws SQLException, InterruptedException {
//        int rounds = Integer.parseInt(gameSettings.getRounds());
//        int duration = Integer.parseInt(gameSettings.getRoundDuration());
//        var players = UserManager.getInstance().getPlayers();

        // fake data
        int rounds = 3;
        int duration = 10;
        ArrayList<AuthUser> players = new ArrayList<>();
        players.add(new AuthUser("Nayem", ""));
        players.add(new AuthUser("Islam", ""));
        players.add(new AuthUser("Zr", ""));
        Collections.shuffle(players);

        for (int i = 0; i < rounds; i++) {
            for (int j = 0; j < players.size(); j++) {
                System.out.println(players.get(j).getUsername() + " is ready to select");
                resetPlayerSession();
                Timer selectionTimer = new SelectionTimer(10);
                selectionTimer.start();
                selectionTimer.join();
                System.out.println("Players ready to guess");
                Timer submissionTimer = new SubmissionTimer(duration);
                submissionTimer.start();
                submissionTimer.join();
            }
        }
    }

    boolean selectionFullFilled() {
        return selected;
    }

    boolean submissionFullFilled() {
        int totalPlayers = 0;
        try {
            totalPlayers = UserManager.getInstance().getPlayers().size();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return totalValidSubmission == totalPlayers - 1;
    }

    abstract class Timer extends Thread {
        int time;
        Timer() {

        }
        Timer (int duration) {
            time = duration;
        }
        abstract boolean isRunning();
        @Override
        public void run() {
            while(isRunning()) {
                Runnable runnable = () -> {
                    guiTimer.setText(String.valueOf(time));
                };
                Platform.runLater(runnable);
                time--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class SelectionTimer extends Timer {
        SelectionTimer(int duration) {
            super(duration);
        }
        @Override
        boolean isRunning() {
            return (time >= 1 && !selectionFullFilled());
        }
    }

    class SubmissionTimer extends Timer {
        SubmissionTimer(int duration) {
            super(duration);
        }
        @Override
        boolean isRunning() {
            return (time >= 1 && !submissionFullFilled());
        }
    }

    class GUIPlayerCard {
        private final AuthUser authUser;
        private HBox playerCard;

        GUIPlayerCard(AuthUser authUser) {
            this.authUser = authUser;
            playerContainer.getChildren().add(getGUI());
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

            playerCard.getChildren().addAll(playerAvatar, playerInfo, playerStatus);
            return playerCard;
        }

    }
}
