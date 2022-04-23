package com.iDebug.pickloose;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.iDebug.pickloose.network.Deserializer;
import com.iDebug.pickloose.network.JsonDeserializer;

public class User {
    @Expose
    protected String username;
    @Expose
    protected String avatar;

    public User() {

    }

    public User(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
    }
    public static String deserialize(User user) {
        return new JsonDeserializer().deserialize(user);
    }
    public static String deserialize(User user, Deserializer deserializer) {
        return deserializer.deserialize(user);
    }
    public static User serialize(String deserialized) {
        return new Gson().fromJson(deserialized,User.class);
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return AvatarFactory.getAvatar(avatar);
    }
}
