package com.iDebug.pickloose.network.server;

import java.io.IOException;
import java.net.Socket;

public class GameServerListener extends Listener {
    GameServerListener(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    void handle() {
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
                new RemoveUserDispatcher().dispatch(Manager.getInstance().getUser(socket));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
