package com.iDebug.pickloose;

import javafx.scene.control.ChoiceBox;

public class GUIGameSettings {
    private ChoiceBox<String> FXRounds;
    private ChoiceBox<String> FXRoundDuration;
    private ChoiceBox<String> FXDifficulty;
    private ChoiceBox<String> FXMaxGuess;
    public GUIGameSettings(ChoiceBox<String> FXRounds, ChoiceBox<String> FXRoundDuration,
                    ChoiceBox<String> FXDifficulty, ChoiceBox<String> FXMaxGuess) {
        this.FXRounds = FXRounds;
        this.FXRoundDuration = FXRoundDuration;
        this.FXDifficulty = FXDifficulty;
        this.FXMaxGuess = FXMaxGuess;
    }
    public void set(GameSettings gameSettings) {
        this.FXRounds.setValue(gameSettings.getRounds());
        this.FXRoundDuration.setValue(gameSettings.getRoundDuration());
        this.FXDifficulty.setValue(gameSettings.getDifficulty());
        this.FXMaxGuess.setValue(gameSettings.getMaxGuess());
    }
}
