package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.network.Response;


abstract class Dispatcher {
    abstract void dispatch(Response response) throws Exception;
}
