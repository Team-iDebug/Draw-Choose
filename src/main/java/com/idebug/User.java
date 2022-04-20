package com.idebug;

import com.google.gson.annotations.Expose;

public class User {
    @Expose
    protected String username;
    @Expose
    protected String ip;
    public User(String username, String ip) {
        this.username = username;
        this.ip = ip;
    }
    public static String deserialize(User user) {
        return new JsonDeserializer().deserialize(user);
    }
    public static String deserialize(User user, Deserializer deserializer) {
        return deserializer.deserialize(user);
    }

    public String getUsername() {
        return username;
    }
}
