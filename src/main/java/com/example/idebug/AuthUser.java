package com.example.idebug;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.UUID;

public class AuthUser extends User {
    @Expose
    protected String userid;

    public AuthUser(String userid, String username, String avatar) {
        this.userid = userid;
        this.username = username;
        this.avatar = avatar;
    }

    public AuthUser(String username, String ip) {
        super(username,ip);
        this.userid = UUID.randomUUID().toString();
    }

    public AuthUser(User user) {
        super(user.username, user.avatar);
        this.userid = UUID.randomUUID().toString();
    }

    public static AuthUser serialize(String body) {
        return new Gson().fromJson(body,AuthUser.class);
    }
}
