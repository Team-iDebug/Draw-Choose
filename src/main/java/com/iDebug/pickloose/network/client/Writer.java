package com.iDebug.pickloose.network.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

class Writer extends Thread {
    private PrintWriter out;
    private Socket socket;
    private String msg;

    Writer(Socket socket, String msg) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            System.out.println(msg);
            out.println(msg);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
