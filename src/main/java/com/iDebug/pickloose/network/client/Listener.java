package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.network.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class Listener extends Thread {
    protected BufferedReader in;
    protected Socket socket;

    Listener(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        boolean alive = true;
        String response = null;
        try {
            while (alive) {
                try {
                    response = in.readLine();
                    if(response != null)
                        new ClientDispatcher().dispatch(Response.serialize(response));
                } catch (IOException e) {
//                    e.printStackTrace();
                    alive = false;
                }
            }
        }
        catch (Exception e) {
//            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
                System.out.println("socket closed...");
            }
            catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }
}
