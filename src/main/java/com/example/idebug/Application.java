package com.example.idebug;

import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        WindowManager.getWindowManager().setStage(stage);
        WindowManager.getWindowManager().setScene(SCENES.HOMEPAGE);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}