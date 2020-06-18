package net.glasslauncher.cachemanager.web;

import com.google.gson.Gson;
import net.glasslauncher.jsontemplate.Profile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebUtils {
    public static String getStringFromURL(String url) throws IOException {
        HttpURLConnection req = (HttpURLConnection) new URL(url).openConnection();
        BufferedReader res = new BufferedReader(new InputStreamReader(req.getInputStream()));
        StringBuilder resj = new StringBuilder();
        for (String strline = ""; strline != null; strline = res.readLine()) {
            resj.append(strline);
        }
        return resj.toString();
    }

    public static String getUUID(String username) throws IOException {
        Profile profile = (new Gson()).fromJson(getStringFromURL("https://api.mojang.com/users/profiles/minecraft/" + username), Profile.class);
        return profile.getId();
    }

}