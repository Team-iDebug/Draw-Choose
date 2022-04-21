package com.example.idebug;

import com.example.idebug.network.*;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.time.LocalTime;

public class Message {
    enum NetworkStatus {
        SENT,
        RECEIVED
    }

    @Expose
    private String userid;
    @Expose
    private String time;
    @Expose
    private String text;
    @Expose
    private NetworkStatus status;

    Message(String userid, String time, String text, NetworkStatus status) {
        this.userid = userid;
        this.time = time;
        this.text = text;
        this.status = status;
    }

    public static Message serialize(String msg) {
        return new Gson().fromJson(msg,Message.class);
    }

    public static String deSerialize(Message msg) {
        return new JsonDeserializer().deserialize(msg);
    }

    public String getText() {
        return text;
    }

    public String getUserid() {
        return userid;
    }

    public String getTime() {
        return time;
    }

    public NetworkStatus getStatus() {
        return status;
    }

    public static void main(String[] args) {
        Message msg = new Message("nayem", LocalTime.now().toString(),"hello world", Message.NetworkStatus.RECEIVED);
        String s = Message.deSerialize(msg);
        System.out.println(s);
        Message msg1 = Message.serialize(s);

//        String t = "{\"userid\":\"nayem\",\"text\":\"hello world\",\"status\":\"RECEIVED\"}";
//        System.out.println(Message.serialize(t).time);
    }
}
