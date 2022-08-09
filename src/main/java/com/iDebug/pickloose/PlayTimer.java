package com.iDebug.pickloose;

import com.iDebug.pickloose.fxcontroller.FXCanvasController;
import javafx.application.Platform;
import javafx.scene.control.Label;


public class PlayTimer extends Timer {
    private static int duration = 0;
    private static boolean finished = false;
    private final Label guiTimer;
    private int time;

    public PlayTimer() {
        duration = Integer.parseInt(GameManager.getInstance().getGameSettings().getRoundDuration());
        guiTimer = FXCanvasController.getGuiTimer();
        finished = false;
    }

    public static void setFinished() {
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    boolean isRunning() {
        return (time >= 1 && !isFinished());
    }

    @Override
    public void run() {
        Thread t = new Thread(() -> {
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
        });
        t.setDaemon(true);
    }
}
