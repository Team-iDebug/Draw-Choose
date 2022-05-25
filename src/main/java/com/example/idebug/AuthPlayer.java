package com.example.idebug;

import java.util.UUID;

public class AuthPlayer extends Player {
    enum STATUS {
        IDLE,
        DRAWING,
        KEY_PRESSING
    }
    private String userid;
    private STATUS status;

    public AuthPlayer(String username, String avatarCode, String ip) {
        super(username, avatarCode, ip);
        this.userid = UUID.randomUUID().toString();
        this.status = STATUS.IDLE;
    }

    public AuthPlayer(Player player) {
        super(player);
        this.userid = UUID.randomUUID().toString();
        this.status = STATUS.IDLE;
    }

    public String getUserid() {
        return userid;
    }
    public STATUS getStatus() {
        return status;
    }
}
