package com.example.idebug.network;

import com.google.gson.JsonObject;

public abstract class Serializer {
    public abstract Object serialize(String deserialized);
    public abstract Object serialize(String deserialized, Class T);
}
