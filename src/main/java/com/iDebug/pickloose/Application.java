package com.iDebug.pickloose;

import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        WindowManager.getInstance().setStage(stage);
        WindowManager.getInstance().setScene(SCENES.GAME);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}