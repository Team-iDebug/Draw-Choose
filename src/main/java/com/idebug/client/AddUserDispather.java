package com.idebug.client;

import com.idebug.Response;

class AddUserDispather {
    public void dispatch(Response response) {
        System.out.println(response.getBody());
    }
}
