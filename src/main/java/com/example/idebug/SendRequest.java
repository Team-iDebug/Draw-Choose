package com.example.idebug;

public class SendRequest implements Request {
    REQ_CODE req;
    Player sender;

    @Override
    public REQ_CODE getReq() {
        return req;
    }

    @Override
    public Player getSender() {
        return sender;
    }
}
