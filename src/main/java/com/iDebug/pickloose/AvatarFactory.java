package com.iDebug.pickloose;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AvatarFactory {
    final private static String location = "src/main/resources/com/iDebug/pickloose/avatar/avatar.json";
    private static AvatarFactory gifFactory;
    private JsonObject avatars;

    private AvatarFactory() {
        avatars = null;
    }

    public static AvatarFactory getInstance() {
        if(gifFactory == null)
            gifFactory = new AvatarFactory();
        return gifFactory;
    }

    private void load () {
        StringBuffer buffer = new StringBuffer();
        System.getProperty("user.dir");
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
        avatars = new Gson().fromJson(buffer.toString(),JsonObject.class);
    }

    public String getAvatar(String code) {
        if(avatars == null || !avatars.isJsonNull())
            load();
        if(avatars.has(code))
            return avatars.get(code).getAsString();
        return null;
    }
}
