package com.iDebug.pickloose.fxcontroller;

import com.iDebug.pickloose.GameManager;
import com.iDebug.pickloose.UserManager;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class FXGameController {
    @FXML
    BorderPane FXParent;

    static BorderPane parent;

    public static BorderPane getParent() {
        return parent;
    }

    @FXML
    public void initialize() {
        parent = FXParent;
        try {
            if(UserManager.getInstance().isHost(UserManager.getInstance().getMyUser().getUserid()))
            {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
