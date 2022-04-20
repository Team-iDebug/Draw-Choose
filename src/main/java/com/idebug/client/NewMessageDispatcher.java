package com.idebug.client;

import com.idebug.Response;

class NewMessageDispatcher {
    void dispatch(Response response) {
        System.out.println(response.getBody());
    }
}
