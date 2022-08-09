package com.iDebug.pickloose;

public class GameSettings {
    static final int selectionDuration = 10;
    private String rounds;
    private String roundDuration;
    private String difficulty;
    private String maxGuess;

    public GameSettings(String rounds, String roundDuration, String difficulty, String maxGuess) {
        this.rounds = rounds;
        this.roundDuration = roundDuration;
        this.difficulty = difficulty;
        this.maxGuess = maxGuess;
    }

    public String getRounds() {
        return rounds;
    }

    public void setRounds(String rounds) {
        this.rounds = rounds;
    }

    public String getRoundDuration() {
        return roundDuration;
    }

    public void setRoundDuration(String roundDuration) {
        this.roundDuration = roundDuration;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getMaxGuess() {
        return maxGuess;
    }

    public void setMaxGuess(String maxGuess) {
        this.maxGuess = maxGuess;
    }

    public int getSelectionDuration() {
        return selectionDuration;
    }
}
