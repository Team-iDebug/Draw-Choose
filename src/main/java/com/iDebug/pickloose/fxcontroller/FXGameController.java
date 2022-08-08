package com.iDebug.pickloose.fxcontroller;

import com.iDebug.pickloose.GameManager;
import javafx.fxml.FXML;

public class FXGameController {
    @FXML
    public void initialize() {
        Thread gameManagerThread = new Thread(() -> {
            try {
                GameManager.getInstance().startGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        gameManagerThread.setDaemon(true);
        gameManagerThread.start();
    }
}
