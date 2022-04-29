package com.iDebug.pickloose;

import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setOnCloseRequest(e -> {
            stage.close();
            // stop all threads
        });
        WindowManager.getInstance().setStage(stage);
        WindowManager.getInstance().setScene(SCENES.HOMEPAGE);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}