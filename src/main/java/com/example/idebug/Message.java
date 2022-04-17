package com.example.idebug;

import java.time.LocalTime;

public class Message {
    enum NetworkStatus {
        DELIVERED,
        SENT,
        RECEIVED
    }

    private String text;
    private String userid;
    private LocalTime time;
    private NetworkStatus status;

    Message(String userid, LocalTime time, String text, NetworkStatus status) {
        this.userid = userid;
        this.time = time;
        this.text = text;
        this.status = status;
    }

    public static Message serialize(String msg) {
        // need to implement
        return null;
    }

    public static String deSerialize(Message msg) {
        // need to implement
        return null;
    }

    public String getText() {
        return text;
    }

    public String getUserid() {
        return userid;
    }

    public LocalTime getTime() {
        return time;
    }

    public NetworkStatus getStatus() {
        return status;
    }
}
