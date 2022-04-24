package com.iDebug.pickloose.network;

import com.iDebug.pickloose.User;
import com.iDebug.pickloose.network.client.Client;
import com.iDebug.pickloose.network.server.GameServerListener;
import com.iDebug.pickloose.network.server.Server;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        /*
        Server server = null;
        Client client = null;

        User user = new User("nayemislamzr", "192.168.0.1");

        String req1 = Request.deserialize(new Request(user, SERVICE.ADD_USER));
        String req2 = Request.deserialize(new Request(user, SERVICE.NEW_MESSAGE, "Hello World"));

        try {
//            server = new Server(6969,new GameServerListener());
//            server.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            client = new Client("127.0.0.1",6969);
            client.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        client.sendMsg(req1);
        client.sendMsg(req2);
        client.sendMsg(req2);

         */
    }
}
