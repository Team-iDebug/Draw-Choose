package com.iDebug.pickloose;

public abstract class Timer extends Thread {
    int time;

    Timer() {
    }

    Timer(int duration) {
        time = duration;
    }

    abstract boolean isRunning();
}
