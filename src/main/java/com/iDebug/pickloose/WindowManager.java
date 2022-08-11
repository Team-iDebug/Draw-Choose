package com.iDebug.pickloose;

import com.iDebug.pickloose.animation.FadeIn;
import com.iDebug.pickloose.animation.SlideInUp;
import com.iDebug.pickloose.animation.SlideOutDown;
import com.iDebug.pickloose.animation.SlideOutUp;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class WindowManager {
    private static WindowManager windowManager;
    private Stage stage;
    private SCENES scene;
    private Parent root;
    private static String rewardSound = "src\\main\\resources\\com\\iDebug\\pickloose\\music\\reward.wav";
    private static String timeUpSound = "src\\main\\resources\\com\\iDebug\\pickloose\\music\\timeUp.wav";
    private Media media = null;

    private WindowManager() {
        media = new Media(new File(rewardSound).toURI().toString());
    }
    public static WindowManager getInstance() {
        if(windowManager == null)
            windowManager = new WindowManager();
        return windowManager;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private void setScene(String fxmlFile, String cssFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        root = scene.getRoot();
        String css = getClass().getResource(cssFile).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("PickLoose");
        stage.setScene(scene);
        new FadeIn(scene.getRoot()).play();
    }
    public void setScene(SCENES scene) {
        this.scene = scene;
        try {
            switch (scene) {
                case HOMEPAGE -> setScene("fxml/homepage/homepage.fxml", "css/homepage.css");
                case LOBBY -> {
                    if(GameManager.getInstance().getUserMode().equals(USER_MODE.HOST))
                        setScene("fxml/lobby/lobby-host.fxml", "css/lobby.css");
                    else
                        setScene("fxml/lobby/lobby-normal.fxml","css/lobby.css");
                }
                case GAME -> setScene("fxml/game/main.fxml","css/game.css");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public SCENES getScene() {
        return scene;
    }

    public void announcePlayer(String player) {
        BoxBlur blur = new BoxBlur(3,3,3);
        root.setEffect(blur);

        BorderPane pauseRoot = new BorderPane();
        pauseRoot.setStyle("-fx-background-color: transparent;");
        Label label = new Label(player + " is drawing.");
        label.setMinWidth(700);
        label.setMinHeight(400);
        label.setStyle("-fx-font-size: 32px;"+
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #F7B733;" +
                "-fx-alignment: center;");
        pauseRoot.setCenter(label);

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(stage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));

        popupStage.show();
        new SlideInUp(label).play();

        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    new SlideOutUp(label).play();
                });
            }
        };

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    root.setEffect(null);
                    popupStage.hide();
                    popupStage.close();
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,3000);
        timer.schedule(task1,2500);
    }

    public void showResult(HashMap<String, Object> map) {
        BoxBlur blur = new BoxBlur(3,3,3);
        root.setEffect(blur);

        BorderPane pauseRoot = new BorderPane();
        VBox vBox = new VBox();
        pauseRoot.setStyle("-fx-background-color: transparent;");
        vBox.setMinHeight(400);
        vBox.setAlignment(Pos.CENTER);

        map.forEach((player,object)-> {
            Double points = (Double)object;
            HBox hBox = new HBox();
            hBox.setMinWidth(150);
            HBox hBox1 = new HBox();
            HBox.setHgrow(hBox1, Priority.ALWAYS);
            Label label1 = new Label(player);
            label1.setStyle("-fx-font-size: 22px;"+
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #F7B733;" +
                    "-fx-alignment: center;");
            hBox1.getChildren().add(label1);
            Label label2 = new Label();
            label2.setText("  + " + points);
            label2.setStyle("-fx-font-size: 22px;"+
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #F7B733;" +
                    "-fx-alignment: center;");
            hBox.getChildren().add(hBox1);
            hBox.getChildren().add(label2);
            vBox.getChildren().add(hBox);
        });

        pauseRoot.setCenter(vBox);

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(stage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));

        Media media = new Media(new File(rewardSound).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);

        popupStage.show();
        new SlideInUp(vBox).play();
        mediaPlayer.play();

        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    new SlideOutUp(vBox).play();
                });
            }
        };

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    root.setEffect(null);
                    popupStage.hide();
                    popupStage.close();
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task1, 2000);
        timer.schedule(task,3000);
    }

    public void timeIsUp(String word) {
        BoxBlur blur = new BoxBlur(3,3,3);
        root.setEffect(blur);

        BorderPane pauseRoot = new BorderPane();
        pauseRoot.setStyle("-fx-background-color: transparent;");
        Label label = new Label("Time is up");
        label.setWrapText(true);
        label.setMinWidth(700);
        label.setMinHeight(400);
        label.setStyle("-fx-font-size: 32px;"+
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #F7B733;" +
                "-fx-alignment: center;");
        pauseRoot.setCenter(label);

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(stage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));

        Media media = new Media(new File(timeUpSound).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);

        popupStage.show();
        new SlideInUp(label).play();
        mediaPlayer.play();

        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    new SlideOutUp(label).play();
                });
            }
        };

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    label.setText("The word was " + word);
                    new SlideInUp(label).play();
                });
            }
        };

        TimerTask task3 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    new SlideOutUp(label).play();
                });
            }
        };

        TimerTask task4 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    root.setEffect(null);
                    popupStage.hide();
                    popupStage.close();
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(task1,2000);
        timer.schedule(task2,3000);
        timer.schedule(task3,5000);
        timer.schedule(task4,6000);
    }
}
