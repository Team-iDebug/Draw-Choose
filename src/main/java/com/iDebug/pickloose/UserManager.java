package com.iDebug.pickloose;

import java.util.HashMap;

public class UserManager {
    private static UserManager userManager;
    private HashMap<String, AuthUser> users;
    private SCENES guiListener;

    private UserManager() {
        users = new HashMap<>();
        guiListener = null;
    }
    public static UserManager getInstance() {
        if(userManager == null)
            userManager = new UserManager();
        return userManager;
    }
    public void setGuiListener(SCENES listener) {
        this.guiListener = listener;
    }
    public SCENES getGUiListener() {
        return guiListener;
    }
    public HashMap<String, AuthUser> getPlayers() {
        return users;
    }
    public void add(AuthUser user) {
        if(users.containsKey(user.getToken()))
            return;
        users.put(user.getToken(),user);
        switch (guiListener) {
            case LOBBY -> LobbyManager.getInstance().addPlayer(user);
        }
    }
    public void remove(AuthUser user) {
        if(!users.containsKey(user.getToken()))
            return;
        users.remove(user.getToken());
        switch (guiListener) {
            case LOBBY -> LobbyManager.getInstance().removePlayer(user);
        }
    }
}
