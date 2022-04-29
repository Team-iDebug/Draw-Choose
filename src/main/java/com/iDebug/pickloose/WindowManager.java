package com.iDebug.pickloose;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
}
