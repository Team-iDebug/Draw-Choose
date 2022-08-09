package com.iDebug.pickloose;

import com.iDebug.pickloose.animation.FadeIn;
import com.iDebug.pickloose.fxcontroller.FXGameController;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class WindowManager {
    private static WindowManager windowManager;
    private Stage stage;
    private SCENES scene;
    private Scene currentScene;

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
        currentScene = scene;
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
//        BoxBlur blur = new BoxBlur(3,3,3);
//        currentScene.getRoot().setEffect(blur);
    }
}
