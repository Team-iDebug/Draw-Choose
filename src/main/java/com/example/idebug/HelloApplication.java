package com.example.idebug;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/iDebug.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = getClass().getResource("css/iDebug.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("iDebug");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}