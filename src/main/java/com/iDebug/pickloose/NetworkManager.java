package com.iDebug.pickloose;

import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.SERVICE;
import com.iDebug.pickloose.network.client.Client;


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
        Client client = NetworkManager.getInstance().getGameClient();
        AuthUser user = NetworkManager.getInstance().getAuthUser();
        Request request = new Request(user,service,text);
        client.sendMsg(Request.deserialize(request));
    }

    public void sendReqAsUser(SERVICE service, String text) {
        Client client = NetworkManager.getInstance().getGameClient();
        User user = NetworkManager.getInstance().getUser();
        Request request = new Request(user,service,text);
        client.sendMsg(Request.deserialize(request));
    }

    public void sendReqAsAuthUser(SERVICE service) {
        Client client = NetworkManager.getInstance().getGameClient();
        User user = NetworkManager.getInstance().getUser();
        Request request = new Request(user,service);
        client.sendMsg(Request.deserialize(request));
    }

    public void sendStream(String msg) {
        try {
            Client streamClient = NetworkManager.getInstance().streamClient;
            streamClient.sendMsg(msg);
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
