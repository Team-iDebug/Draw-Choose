package com.example.idebug;

public class Player {
    protected String username;
    protected String avatarCode;
    protected String ip;

    public Player(Player player) {
        this.username = player.username;
        this.avatarCode = player.avatarCode;
        this.ip = player.ip;
    }

    public Player(String username, String avatarCode, String ip) {
        this.username = username;
        this.avatarCode = avatarCode;
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarCode() {
        return avatarCode;
    }

    public String getIp() {
        return ip;
    }
}
