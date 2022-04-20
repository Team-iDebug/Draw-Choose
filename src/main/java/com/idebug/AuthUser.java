package com.idebug;

import com.google.gson.annotations.Expose;
import com.idebug.Main;

import java.util.UUID;

public class AuthUser extends User {
    @Expose
    protected String userid;

    public AuthUser(String username, String ip) {
        super(username,ip);
        this.userid = UUID.randomUUID().toString();
    }

    public AuthUser(User user) {
        super(user.username, user.ip);
        this.userid = UUID.randomUUID().toString();
    }
}
