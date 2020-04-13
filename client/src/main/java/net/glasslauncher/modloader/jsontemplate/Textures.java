package net.glasslauncher.modloader.jsontemplate;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Textures {

    @SerializedName("SKIN")
    private SkinURL skin = new SkinURL();

    @SerializedName("CAPE")
    private SkinURL cape = new SkinURL();
}
