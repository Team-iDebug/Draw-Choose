package com.iDebug.pickloose;

public class GameManager {
    private static GameManager gameManager;
    private USER_MODE userMode;
    private GameManager() {

    }
    public static GameManager getInstance() {
        if(gameManager == null)
            gameManager = new GameManager();
        return gameManager;
    }
    public void setUserMode(USER_MODE mode) {
        userMode = mode;
    }
    public USER_MODE getUserMode() {
        return userMode;
    }
}
