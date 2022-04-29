package com.iDebug.pickloose;

import javafx.scene.control.Label;

public class GUIGameSettings {
    private Label FXRounds;
    private Label FXRoundDuration;
    private Label FXDifficulty;
    private Label FXMaxGuess;
    public GUIGameSettings(Label FXRounds, Label FXRoundDuration,
                    Label FXDifficulty, Label FXMaxGuess) {
        this.FXRounds = FXRounds;
        this.FXRoundDuration = FXRoundDuration;
        this.FXDifficulty = FXDifficulty;
        this.FXMaxGuess = FXMaxGuess;
    }
    public void set(GameSettings gameSettings) {
        this.FXRounds.setText(gameSettings.getRounds());
        this.FXRoundDuration.setText(gameSettings.getRoundDuration());
        this.FXDifficulty.setText(gameSettings.getDifficulty());
        this.FXMaxGuess.setText(gameSettings.getMaxGuess());
    }
}
