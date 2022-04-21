package com.example.idebug.network;

import com.example.idebug.AuthUser;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class Response {
    @Expose
    private SERVICE service;
    @Expose
    private FEEDBACK status;
    @Expose
    private String body;

    public Response(SERVICE service, FEEDBACK status) {
        this.service = service;
        this.status = status;
    }

    public Response(SERVICE service, FEEDBACK status, String body) {
        this.service = service;
        this.status = status;
        this.body = body;
    }

    public SERVICE getService() {
        return service;
    }

    public FEEDBACK getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public static String deserialize(Response response) {
        return new JsonDeserializer().deserialize(response);
    }

    public static String deserialize(Request request, Deserializer deserializer) {
        return deserializer.deserialize(request);
    }

    public static Response serialize(String deserialized) {
        return (Response) new JsonSerializer().serialize(deserialized, Response.class);
    }

    public static Response serialize(String deserialized, Serializer serializer) {
        return (Response) serializer.serialize(deserialized,Response.class);
    }

    public static void main(String[] args) {
        AuthUser user = new AuthUser("nayem","192.168.0.1");
        Response response = new Response(SERVICE.ADD_USER, FEEDBACK.SUCCEED, new Gson().toJson(user,AuthUser.class));
        System.out.println(response.status);
        String deserialized = Response.deserialize(response);
        System.out.println(deserialized);
    }
}
