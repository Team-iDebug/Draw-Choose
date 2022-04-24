package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.network.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class GameClientListener extends Listener {
    GameClientListener(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    void handle() {
        String response = null;
        System.out.println("started listening...");
        try {
            while (true) {
                try {
                    response = in.readLine();
                    if(response != null)
                        new ClientDispatcher().dispatch(Response.serialize(response));
                } catch (IOException e) {
                    e.printStackTrace();
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
