package com.iDebug.pickloose.fxcontroller;

import com.google.gson.Gson;
import com.iDebug.pickloose.*;
import com.iDebug.pickloose.network.SERVICE;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;


public class FXHostLobbyController {
    @FXML
    private VBox FXPlayerContainer;
    @FXML
    private Label FXServerAddress;
    @FXML
    private ChoiceBox<String> FXRounds;
    @FXML
    private ChoiceBox<String> FXRoundDuration;
    @FXML
    private ChoiceBox<String> FXDifficulty;
    @FXML
    private ChoiceBox<String> FXMaxGuess;
    @FXML
    private Button FXGameInvoker;
    @FXML
    private HBox FXCopyBtn;

    private String rounds[] = {"1","2","4","8","16"};
    private String roundDuration[] = {"30","60","90","120","180","360","720"};
    private String difficulties[] = {"Easy","Medium","Hard"};
    private String Guesses[] = {"Unlimited", "1","2","4","8"};

    private String oldRoundVal = "8";
    private String oldRoundDurationVal = "180";
    private String oldDiffVal = "EASY";
    private String oldGuessVal = "Unlimited";

    // game settings controller
    private void setupGameSettings() {
        ChoiceBox boxes[] = {
                FXRounds,
                FXRoundDuration,
                FXDifficulty,
                FXMaxGuess
        };

        FXRounds.getItems().addAll(rounds);
        FXRoundDuration.getItems().addAll(roundDuration);
        FXDifficulty.getItems().addAll(difficulties);
        FXMaxGuess.getItems().addAll(Guesses);

        FXRounds.setValue(oldRoundVal);
        FXRoundDuration.setValue(oldRoundDurationVal);
        FXDifficulty.setValue(oldDiffVal);
        FXMaxGuess.setValue(oldGuessVal);

        GameSettings gameSettings = new GameSettings(FXRounds.getValue(),FXRoundDuration.getValue(),
                FXDifficulty.getValue(),FXMaxGuess.getValue());
        String serializedGameSettings = new Gson().toJson(gameSettings);
        NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.SET_GAME_SETTINGS, serializedGameSettings);

        for(var box : boxes) {
            box.setOnAction(e -> {
                String newRoundVal = FXRounds.getValue();
                String newRoundDurationVal = FXRoundDuration.getValue();
                String newDiffVal = FXDifficulty.getValue();
                String newGuessVal = FXMaxGuess.getValue();

                if(!newRoundVal.equals(oldRoundVal) || !newRoundDurationVal.equals(oldRoundDurationVal)
                        || !newDiffVal.equals(oldDiffVal) || !newGuessVal.equals(oldGuessVal)) {

                    GameSettings newGameSettings = new GameSettings(newRoundVal,newRoundDurationVal,
                            newDiffVal,newGuessVal);
                    String newSerializedGameSettings = new Gson().toJson(newGameSettings);
                    NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.SET_GAME_SETTINGS, newSerializedGameSettings);

                    oldRoundVal = newRoundVal;
                    oldRoundDurationVal = newRoundDurationVal;
                    oldDiffVal = newDiffVal;
                    oldGuessVal = newGuessVal;
                }
            });
        }
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
            NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.START_GAME);
        });
    }

    @FXML
    public void initialize() {
        LobbyManager.getInstance().setContainer(FXPlayerContainer);
        setupGameSettings();
        setupHelperFunc();
    }
}
