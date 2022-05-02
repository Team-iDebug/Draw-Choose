package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.network.Response;
import java.io.IOException;
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
            boolean alive = true;
            while (alive) {
                try {
                    response = in.readLine();
                    System.out.println("client : " + response);
                    if(response != null)
                        new ClientDispatcher().dispatch(Response.serialize(response));
                } catch (IOException e) {
                    e.printStackTrace();
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
