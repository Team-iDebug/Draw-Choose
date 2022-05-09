package com.iDebug.pickloose;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GifFactory {
    final private static String location = "src/main/resources/com/iDebug/pickloose/gifs/gifs.json";
    private static GifFactory gifFactory;
    private JsonObject gifs;

    private GifFactory() {
        gifs = null;
    }

    public static GifFactory getInstance() {
        if(gifFactory == null)
            gifFactory = new GifFactory();
        return gifFactory;
    }

    private void load () {
        StringBuffer buffer = new StringBuffer();
        try {
            File f = new File(location);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine())
                buffer.append(scanner.nextLine());
            scanner.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        gifs = new Gson().fromJson(buffer.toString(),JsonObject.class);
    }

    public String getGif(String code) {
        if(gifs == null || !gifs.isJsonNull())
            load();
        if(gifs.has(code))
            return gifs.get(code).getAsJsonObject().get("gif").getAsString();
        return null;
    }
}

