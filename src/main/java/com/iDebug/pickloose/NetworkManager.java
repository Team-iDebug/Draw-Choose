package com.iDebug.pickloose;

import com.iDebug.pickloose.network.Request;
import com.iDebug.pickloose.network.SERVICE;
import com.iDebug.pickloose.network.client.Client;


public class NetworkManager {
    private static NetworkManager networkManager;
    private Client client;
    private AuthUser authUser;
    private User user;

    protected NetworkManager() {

    }
    public static NetworkManager getInstance() {
        if(networkManager == null)
            networkManager = new NetworkManager();
        return networkManager;
    }

    public static void sendReqAsAuthUser(SERVICE service, String text) {
        Client client = NetworkManager.getInstance().getClient();
        AuthUser user = NetworkManager.getInstance().getAuthUser();
        Request request = new Request(user,service,text);
        client.sendMsg(Request.deserialize(request));
    }

    public static void sendReqAsUser(SERVICE service, String text) {
        Client client = NetworkManager.getInstance().getClient();
        User user = NetworkManager.getInstance().getUser();
        Request request = new Request(user,service,text);
        client.sendMsg(Request.deserialize(request));
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
}
