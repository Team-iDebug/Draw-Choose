package com.iDebug.pickloose.fxcontroller;

import com.iDebug.pickloose.LobbyManager;
import com.iDebug.pickloose.NetworkManager;
import com.iDebug.pickloose.Player;
import com.iDebug.pickloose.network.SERVICE;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class FXLobbyController {
    @FXML
    private VBox playerContainer;
    @FXML
    private Label serverAddress;

    @FXML
    public void initialize() {
        LobbyManager.getInstance().setContainer(playerContainer);
        try {
            String serverIP = NetworkManager.getInstance().getGameClient().getIp();
            int serverPort = NetworkManager.getInstance().getGameClient().getPort();
            serverAddress.setText(serverIP+":"+serverPort);
        }
        catch (NullPointerException e) {
            System.out.println("server address not found!!!");
        }
        NetworkManager.getInstance().sendReqAsAuthUser(SERVICE.GET_ALL_MEMEBER);
    }
}
