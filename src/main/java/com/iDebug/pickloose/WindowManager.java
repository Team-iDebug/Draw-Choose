package com.iDebug.pickloose;

import com.iDebug.pickloose.fxcontroller.FXGameController;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class WindowManager {
    private static WindowManager windowManager;
    private Stage stage;
    private SCENES scene;

    private WindowManager() {
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
        String css = getClass().getResource(cssFile).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("PickLoose");
        stage.setScene(scene);
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
    public void slideShow(Parent slide) {
        BorderPane parent = FXGameController.getParent();
        slide.translateYProperty().set(stage.getHeight());
        parent.getChildren().add(slide);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(slide.translateYProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1),kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            parent.getChildren().remove(slide);
        });
        timeline.play();
    }

    public void announcePlayer(String player) {
//        Parent parent = null;
//        try {
//            parent = FXMLLoader.load(getClass().getResource("fxml/game/slider.fxml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        slideShow(parent);
        BorderPane parent = FXGameController.getParent();
//        parent.setStyle();
    }
}
