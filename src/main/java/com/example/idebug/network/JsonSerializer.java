package com.example.idebug.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class JsonSerializer extends Serializer {
    @Override
    public Object serialize(String deserialized) {
        return new Gson().fromJson(deserialized, JsonObject.class);
    }
    @Override
    public Object serialize(String deserialized, Class T) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(deserialized, T);
    }
}
