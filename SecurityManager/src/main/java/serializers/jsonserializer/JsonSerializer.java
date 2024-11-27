package serializers.jsonserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.SecurityManager;
import serializers.jsonserializer.adapters.SecurityManagerAdapter;

import java.io.*;

public class JsonSerializer {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(SecurityManager.class, new SecurityManagerAdapter())
            .create();

    public static <T> void serialize(T object, String filePath) throws IOException {
        try (Writer writer = new FileWriter(filePath, false)) {
            gson.toJson(object, writer);
        }
    }

    public static <T> T deserialize(String filePath, Class<T> clazz) throws IOException {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, clazz);
        }
    }
}

