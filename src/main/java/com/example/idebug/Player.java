package com.example.idebug;

import com.example.idebug.network.Request;
import com.example.idebug.network.SERVICE;
import com.example.idebug.network.client.Client;

public class Player {
    private static Player player;
    private Client client;
    private AuthUser authUser;
    private User user;

    protected Player() {

    }
    public static Player getInstance() {
        if(player == null)
            player = new Player();
        return player;
    }

    public static void sendReqAsAuthUser(SERVICE service, String text) {
        Client client = Player.getInstance().getClient();
        AuthUser user = Player.getInstance().getAuthUser();
        Request request = new Request(user,service,text);
        client.sendMsg(Request.deserialize(request));
    }

    public static void sendReqAsUser(SERVICE service, String text) {
        Client client = Player.getInstance().getClient();
        User user = Player.getInstance().getUser();
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
