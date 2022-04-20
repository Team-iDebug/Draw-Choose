package com.idebug.server;

import com.idebug.server.ServerDispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerListener extends Thread {
    protected BufferedReader in;
    protected Socket socket;

    ServerListener(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            boolean alive = true;
            String req = null;
            while (alive) {
                try {
                    req = in.readLine();
                    if(req != null) {
                        new ServerDispatcher(socket, req).start();
                    }
                } catch (IOException e) {
//                    e.printStackTrace();
                    alive = false;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
                System.out.println("socket closed.");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
