package com.iDebug.pickloose;

import com.iDebug.pickloose.animation.Bounce;
import com.iDebug.pickloose.animation.FadeIn;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WindowManager {
    private static WindowManager windowManager;
    private Stage stage;
    private SCENES scene;
    private Parent root;
    private static String path = "D:\\softwareDev\\iDebug\\src\\main\\resources\\com\\iDebug\\pickloose\\music\\reward.wav";
    private Media media = null;

    private WindowManager() {
        media = new Media(new File(path).toURI().toString());
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
        Label label = new Label(player + " is choosing drawing.");
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
        new Bounce(label).play();

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
    }

    public void showResult(HashMap<String, Object> map) {
        BoxBlur blur = new BoxBlur(3,3,3);
        root.setEffect(blur);

        BorderPane pauseRoot = new BorderPane();
        VBox vBox = new VBox();
        pauseRoot.setStyle("-fx-background-color: transparent;");

        map.forEach((player,object)-> {
            Double points = (Double)object;
            HBox hBox = new HBox();
            Label label1 = new Label(player);
            label1.setStyle("-fx-font-size: 22px;"+
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #F7B733;" +
                    "-fx-alignment: center;");
            Label label2 = new Label();
            label2.setText("   + " + points);
            label2.setStyle("-fx-font-size: 22px;"+
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #F7B733;" +
                    "-fx-alignment: center;");
            hBox.getChildren().add(label1);
            hBox.getChildren().add(label2);
            vBox.getChildren().add(hBox);
        });

        pauseRoot.setCenter(vBox);

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(stage);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));

        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);

        popupStage.show();
        mediaPlayer.play();

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
    }
}
