package com.iDebug.pickloose.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonDeserializer extends Deserializer {
    @Override
    public String deserialize(Object obj) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(obj);
    }
}
