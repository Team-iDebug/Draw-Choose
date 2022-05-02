package com.iDebug.pickloose;

import com.google.gson.Gson;
import com.iDebug.pickloose.canvas.DrawManager;
import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.SERVICE;
import com.iDebug.pickloose.network.client.Client;
import javafx.scene.image.WritableImage;

import java.io.IOException;


public class NetworkManager {
    private static NetworkManager networkManager;
    private Client gameClient;
    private Client streamClient;
    private AuthUser authUser;
    private User user;

    protected NetworkManager() {

    }
    public static NetworkManager getInstance() {
        if(networkManager == null)
            networkManager = new NetworkManager();
        return networkManager;
    }

    public void sendReqAsAuthUser(SERVICE service, String text) {
        Request request = new Request(authUser,service,text);
        if(request.getSender() == null)
            MessageBus.getInstance().add(request);
        else
            gameClient.sendMsg(Request.deserialize(request));
    }

    public void sendReqAsUser(SERVICE service, String text) {
        Request request = new Request(user,service,text);
        gameClient.sendMsg(Request.deserialize(request));
    }

    public void sendReqAsAuthUser(SERVICE service) {
        Request request = new Request(authUser,service);
        if(request.getSender() == null)
            MessageBus.getInstance().add(request);
        else
            gameClient.sendMsg(new Gson().toJson(request));
    }

    public void sendReq(Request request) {
        gameClient.sendMsg(new Gson().toJson(request));
    }

    public void sendToolAction(String x, String y, String action) {
        try {
            String w = String.valueOf(DrawManager.getInstance().getCanvas().getWidth());
            String h = String.valueOf(DrawManager.getInstance().getCanvas().getHeight());
            String toolAction = x+","+y+","+w+","+h+","+action;
//            streamClient.sendToolAction(toolAction);
            streamClient.sendMsg(toolAction);
        }
        catch (NullPointerException e) {
            System.out.println("No Stream Client Found!!!");
        }
    }

    public Client getGameClient() {
        return gameClient;
    }

    public void setGameClient(Client client) {
        this.gameClient = client;
    }

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Client getStreamClient() {
        return streamClient;
    }

    public void setStreamClient(Client streamClient) {
        this.streamClient = streamClient;
    }


}
