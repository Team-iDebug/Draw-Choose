package com.iDebug.pickloose;

import com.iDebug.pickloose.fxcontroller.FXCanvasController;
import javafx.application.Platform;
import javafx.scene.control.Label;


public class PlayTimer extends Timer {
    private static int duration = 0;
    private final Label guiTimer;
    private int time;
    private static PlayTimer playTimer;

    public PlayTimer() {
        playTimer = this;
        duration = Integer.parseInt(GameManager.getInstance().getGameSettings().getRoundDuration());
        guiTimer = FXCanvasController.getGuiTimer();
        time = duration;
    }

    public static void terminate() {
        try {
            playTimer.stop();
        }
        catch (Exception e) {

        }
    }

    public static boolean isDone() {
        if(playTimer == null)
            return true;
        return playTimer.isAlive();
    }

    boolean isRunning() {
        return (time >= 1);
    }

    @Override
    public void run() {
        while (isRunning()) {
            Platform.runLater(() -> {
                guiTimer.setText(String.valueOf(time));
            });
            time--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
