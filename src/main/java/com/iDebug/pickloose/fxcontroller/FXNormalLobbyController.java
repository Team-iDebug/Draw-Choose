package com.iDebug.pickloose.fxcontroller;

import com.iDebug.pickloose.*;
import com.iDebug.pickloose.network.SERVICE;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class FXNormalLobbyController {
    @FXML
    private VBox FXPlayerContainer;
    @FXML
    private Label FXServerAddress;
    @FXML
    private Label FXRounds;
    @FXML
    private Label FXRoundDuration;
    @FXML
    private Label FXDifficulty;
    @FXML
    private Label FXMaxGuess;
    @FXML
    private Button FXGameInvoker;
    @FXML
    private HBox FXCopyBtn;

    // game settings controller
    private void setupGameSettings() {
        LobbyManager.getInstance().setGuiGameSettings(new GUIGameSettings(FXRounds, FXRoundDuration,
                FXDifficulty, FXMaxGuess));
        NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.GET_GAME_SETTINGS);
    }

    // host vs normal user view
    private void setupHelperFunc() {
        try {
            String serverIP = NetworkManager.getInstance().getGameClient().getIp();
            int serverPort = NetworkManager.getInstance().getGameClient().getPort();
            FXServerAddress.setText(serverIP+":"+serverPort);
        }
        catch (NullPointerException e) {
            System.out.println("server address not found!!!");
        }
        FXCopyBtn.setOnMouseClicked(e -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(FXServerAddress.getText()), null);
        });
        FXGameInvoker.setOnMouseClicked(e -> {
            if(FXGameInvoker.getText().equals("READY")) {
                FXGameInvoker.setText("NOT READY");
                NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.SET_READY);
            }
            else {
                FXGameInvoker.setText("READY");
                NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.SET_NOT_READY);
            }
        });
    }

    @FXML
    public void initialize() {
        setupGameSettings();
        setupHelperFunc();
        // getting already joined members
        LobbyManager.getInstance().setContainer(FXPlayerContainer);
        NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.GET_ALL_MEMBER);
    }
}
