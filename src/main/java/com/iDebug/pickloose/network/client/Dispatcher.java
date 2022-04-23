package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.network.Response;

import java.net.Socket;

abstract class Dispatcher {
    abstract void dispatch(Response response) throws Exception;
}
