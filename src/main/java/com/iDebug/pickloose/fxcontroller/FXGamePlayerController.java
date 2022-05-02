package com.iDebug.pickloose.fxcontroller;

import com.iDebug.pickloose.GameManager;
import com.iDebug.pickloose.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class FXGamePlayerController {
    @FXML
    private VBox FXPlayerContainer;
    @FXML
    private Button FXKickBtn;

    @FXML
    public void initialize() {
        GameManager.getInstance().setPlayerContainer(FXPlayerContainer);
        try {
            var players = UserManager.getInstance().getPlayers();
            for(var player : players) {
                GameManager.getInstance().addPlayer(player);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
