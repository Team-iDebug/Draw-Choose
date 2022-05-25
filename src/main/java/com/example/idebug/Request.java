package com.example.idebug;

public interface Request {
    enum REQ_CODE {
        JOIN,
        FETCH_DATA,
        GET_MESSAGES,
        GET_USERS,
    }

    public REQ_CODE getReq();
    public Player getSender();
}
