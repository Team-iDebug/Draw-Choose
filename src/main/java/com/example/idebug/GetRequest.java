package com.example.idebug;

public class GetRequest implements Request {
    REQ_CODE req;
    Player sender;

    public GetRequest(REQ_CODE req, Player sender) {
        this.req = req;
        this.sender = sender;
    }

    @Override
    public REQ_CODE getReq() {
        return req;
    }

    @Override
    public Player getSender() {
        return sender;
    }
}
