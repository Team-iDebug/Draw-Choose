package com.iDebug.pickloose;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AvatarFactory {
    final private static String location = "src/main/resources/com/example/idebug/avatar/avatar.json";
    static JsonObject avatars = null;

    public static void load () {
        if (avatars != null) // already loaded
            return;
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
        avatars = new Gson().fromJson(buffer.toString(),JsonObject.class);
    }

    public static String getAvatar(String code) {
        load();
        if(avatars.has(code))
            return avatars.get(code).getAsString();
        return null;
    }
}
