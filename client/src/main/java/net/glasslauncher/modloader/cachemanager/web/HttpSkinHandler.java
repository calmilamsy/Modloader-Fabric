package net.glasslauncher.modloader.cachemanager.web;

import com.google.gson.Gson;
import net.glasslauncher.modloader.jsontemplate.MCProfile;
import net.glasslauncher.modloader.jsontemplate.SkinURL;
import net.glasslauncher.modloader.jsontemplate.TextureInfo;
import net.glasslauncher.modloader.jsontemplate.Textures;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HttpSkinHandler {
    private int isCape;

    public static String[] getImages(String username) throws IOException {
        Gson gson = new Gson();
        String uuid = WebUtils.getUUID(username);
        if (uuid == null) {
            return new String[]{"", ""};
        }
        MCProfile profile = gson.fromJson(WebUtils.getStringFromURL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid), MCProfile.class);

        String properties = profile.getProperties()[0].getValue();

        byte[] base64 = properties.getBytes(StandardCharsets.UTF_8);
        TextureInfo textureInfo = gson.fromJson((new String(Base64.getDecoder().decode(base64))), TextureInfo.class);

        Textures textures = textureInfo.getTextures();
        SkinURL skin = textures.getSkin();
        SkinURL cape = textures.getCape();

        return new String[]{skin.getUrl(), cape.getUrl()};
    }
}