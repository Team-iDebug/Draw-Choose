package com.iDebug.pickloose;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class Player extends AuthUser {
    @Expose
    protected Integer points;

    public Player(String userid, String username, String avatar) {
        super(userid, username, avatar);
        this.points = 0;
    }

    public Player(String userid, String token, String username, String avatar) {
        super(userid, token, username, avatar);
        this.points = 0;
    }

    public Player(String username, String ip) {
        super(username, ip);
        this.points = 0;
    }

    public Player(User user) {
        super(user);
        this.points = 0;
    }

    public static Player serialize(String body) {
        return new Gson().fromJson(body,Player.class);
    }
}
