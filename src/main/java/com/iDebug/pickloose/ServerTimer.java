package com.iDebug.pickloose;

import java.sql.SQLException;

public class ServerTimer extends Timer {
    static int TIME;

    ServerTimer(int duration) {
        time = duration;
        TIME = time;
    }

    public static int getTime() {
        return TIME;
    }

    @Override
    boolean isRunning() {
        int players = 0;
        try {
            players = UserManager.getInstance().getPlayers().size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int validGuess = GameManager.getInstance().getValidGuessAmount();
        return (validGuess < players-1 && time >= 1);
    }

    @Override
    public void run() {
        while (isRunning()) {
            time--;
            TIME = time;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
