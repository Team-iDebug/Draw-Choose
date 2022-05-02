package com.iDebug.pickloose.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.User;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class Request {
    @Expose
    public User sender;
    @Expose
    public SERVICE service;
    @Expose
    public String body;

    public Request(AuthUser sender, SERVICE service) {
        this.sender = sender;
        this.service = service;
    }

    public Request(AuthUser sender, SERVICE service, String body) {
        this.sender = sender;
        this.service = service;
        this.body = body;
    }

    public Request(User sender, SERVICE service) {
        this.sender = sender;
        this.service = service;
    }

    public Request(User sender, SERVICE service, String body) {
        this.sender = sender;
        this.service = service;
        this.body = body;
    }

    public User getSender() {
        return sender;
    }

    public AuthUser getAuthSender() {return (AuthUser)sender;}

    public SERVICE getService() {
        return service;
    }

    public String getBody() {
        return body;
    }

    public static String deserialize(Request request) {
        return new JsonDeserializer().deserialize(request);
    }

    public static String deserialize(Request request, Deserializer deserializer) {
        return deserializer.deserialize(request);
    }

    public static Request serialize(String deserialized) {
        JsonObject request = new Gson().fromJson(deserialized,JsonObject.class);
        JsonObject sender = request.get("sender").getAsJsonObject();
        User user = null;
        if(sender.has("userid")) {
            user = new Gson().fromJson(sender,AuthUser.class);
        }
        else {
            user = new Gson().fromJson(sender, User.class);
        }
        SERVICE service = new Gson().fromJson(request.get("service"),SERVICE.class);
        if(request.has("body")) {
            String body = request.get("body").getAsString();
            return new Request(user,service,body);
        }
        return new Request(user,service);
    }

    public static Request serialize(String deserialized, Serializer serializer) {
        return (Request) serializer.serialize(deserialized,Request.class);
    }
}
