package com.example.idebug.network;

import com.example.idebug.AuthUser;
import com.example.idebug.User;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Request {
    @Expose
    public User sender;
    @Expose
    public SERVICE service;
    @Expose
    public String body;

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
        return (Request) new JsonSerializer().serialize(deserialized, Request.class);
    }

    public static Request serialize(String deserialized, Serializer serializer) {
        return (Request) serializer.serialize(deserialized,Request.class);
    }

    public static void main(String[] args) {
        AuthUser authUser = new AuthUser("nayemislamzr","192.168.0.1");
        String body = "hello world";
        Request req = new Request(authUser, SERVICE.NEW_MESSAGE,body);
        String desrialized = new Gson().toJson(req);
        Request request = Request.serialize(desrialized);
        System.out.println(request.body);
    }
}
