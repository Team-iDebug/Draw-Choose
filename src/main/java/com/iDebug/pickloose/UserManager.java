package com.iDebug.pickloose;

import java.util.HashMap;

public class UserManager {
    private static UserManager userManager;
    private HashMap<String, AuthUser> users;

    private UserManager() {
        users = new HashMap<>();
    }
    public static UserManager getInstance() {
        if(userManager == null)
            userManager = new UserManager();
        return userManager;
    }
    public HashMap<String, AuthUser> getPlayers() {
        return users;
    }
    public void add(AuthUser user) {
        if(users.containsKey(user.getToken()))
            return;
        users.put(user.getToken(),user);
        LobbyManager.getInstance().addPlayer(user);
    }
}
