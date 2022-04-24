package com.iDebug.pickloose.network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

abstract class Listener {
    protected BufferedReader in;
    protected Socket socket;

    Listener(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void startListening() {
        new Thread(() -> {
            handle();
        }).start();
    }

    abstract void handle();
}
