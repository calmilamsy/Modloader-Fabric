package net.glasslauncher.modloader.cachemanager.utils;

import com.google.gson.Gson;
import lombok.Getter;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonLoader<T> {
    @Getter
    private T jsonObject;

    /**
     * Reads JSON file from disk.
     * Creates an empty JSON object if file can't be read.
     *
     * @param path Path to the JSON file.
     * @throws IOException           if JSON file fails to read.
     * @throws FileNotFoundException if target file does not exist.
     */
    public JsonLoader(String path, T jsonObject) throws IOException {
        this.jsonObject = jsonObject;
        if (!(new File(path)).exists()) {
            throw new FileNotFoundException("File \"" + path + "\" could not be found!");
        }
        try {
            this.jsonObject = (new Gson()).fromJson(readFileToString(path), (Type) jsonObject.getClass());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Failed to read JSON file!");
        }
    }

    /**
     * Saves the JSON object stored in memory.
     *
     * @return true on success, false on an error.
     */
    public boolean saveFile(String path, Object jsonObject) {
        try (PrintStream out = new PrintStream(new FileOutputStream(path))) {
            out.print((new Gson()).toJson(jsonObject));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String readFileToString(String path)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
