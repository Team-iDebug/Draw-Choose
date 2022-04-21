package com.example.idebug;

import com.example.idebug.Player;
import com.example.idebug.network.server.Server;

public class Host extends Player {
    private static Server server;

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        Host.server = server;
    }
}
