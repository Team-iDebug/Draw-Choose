package com.iDebug.pickloose;

import com.iDebug.pickloose.network.server.Server;

public class Host extends NetworkManager {
    private static Server server;

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        Host.server = server;
    }
}
