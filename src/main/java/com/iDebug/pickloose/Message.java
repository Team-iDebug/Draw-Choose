package com.iDebug.pickloose;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.iDebug.pickloose.network.JsonDeserializer;

import java.time.LocalTime;

public class Message {
    @Expose
    private String userid;
    @Expose
    private String time;
    @Expose
    private String text;
    @Expose
    private MSG_TYPE type;
    @Expose
    private MSG_STATUS status;

    public Message(String userid, String time, MSG_TYPE msg_type, String text, MSG_STATUS status) {
        this.userid = userid;
        this.time = time;
        this.type = msg_type;
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

    public MSG_STATUS getStatus() {
        return status;
    }

    public MSG_TYPE getType() {
        return type;
    }
}
