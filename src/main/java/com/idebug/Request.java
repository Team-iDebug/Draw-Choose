package com.idebug;

import com.google.gson.annotations.Expose;

public class Request {
    @Expose
    private User sender;
    @Expose
    private SERVICE service;
    @Expose
    private String body;

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
//        Request req = new Request(REQ.GET, new AuthUser("nayemislamzr","192.168.0.1"),Service.JOIN);
        AuthUser authUser = new AuthUser("nayemislamzr","192.168.0.1");
        String body = "hello world";
        Request req = new Request(authUser, SERVICE.NEW_MESSAGE,body);
        String desrialized = Request.deserialize(req);
        Request request = Request.serialize(desrialized);
        System.out.println(request.body);
    }
}
