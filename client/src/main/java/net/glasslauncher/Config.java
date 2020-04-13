package net.glasslauncher;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;

public class Config {
    // Path for cache and config to be stored.
    public transient static final String cachepath = getAppDir("Glass-Launcher").toString() + "/glass/netfix/";
    public transient static final String confpath = getAppDir("minecraft").toString() + "/config/glass-netfix/";

    @SerializedName("skinagelimit")
    @Getter
    private static long skinAgeLimit = 1800L;

    @SerializedName("docape")
    @Getter
    private boolean doCape = true;

    @SerializedName("dosound")
    @Getter
    private boolean doSound = true;

    @SerializedName("doskin")
    @Getter
    private boolean doSkin = true;

    @SerializedName("doauth")
    @Getter
    private boolean doAuth = true;

    public transient static final HashMap<String, String> depsList = new HashMap<String, String>() {{
        put("littleproxy.jar", "https://github.com/adamfisk/LittleProxy/releases/download/littleproxy-1.1.2/littleproxy-1.1.2-littleproxy-shade.jar,05613C6D1BB1A8F826711BA54569311E");
        put("littleproxy-mitm.jar", "https://repo1.maven.org/maven2/com/github/ganskef/littleproxy-mitm/1.1.0/littleproxy-mitm-1.1.0.jar,B1FD7C2BFCD32BCF5873D298484DABBA");
        put("json-io.jar", "https://repo1.maven.org/maven2/com/cedarsoftware/json-io/4.10.1/json-io-4.10.1.jar,A2A0BD324F4A24D164B037529EA2391A");
        put("bcpkix-jdk15on.jar", "https://repo1.maven.org/maven2/org/bouncycastle/bcpkix-jdk15on/1.63/bcpkix-jdk15on-1.63.jar,C7DC9B66A0535F44DD088BABEA47B506");
        put("bcprov-jdk15on.jar", "https://repo1.maven.org/maven2/org/bouncycastle/bcprov-jdk15on/1.63/bcprov-jdk15on-1.63.jar,D357114F1605C034EBCB99F3C9D36F7E");
        put("commons-io.jar", "https://repo1.maven.org/maven2/commons-io/commons-io/2.4/commons-io-2.4.jar,7F97854DC04C119D461FED14F5D8BB96");
        put("gson.jar", "https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.6/gson-2.8.6.jar,310f5841387183aca7900fead98d4858");
    }};

    // Snippets copied from Minecraft.class

    public static File getAppDir(String s) {
        if (isServer()) {
            return new File(".");
        }
        String s1 = System.getProperty("user.home", ".");
        File file;
        switch (EnumOSMappingHelper.enumOSMappingArray[getOs().ordinal()]) {
            case 1: // '\001'
            case 2: // '\002'
                file = new File(s1, '.' + s + '/');
                break;

            case 3: // '\003'
                String s2 = System.getenv("APPDATA");
                if (s2 != null) {
                    file = new File(s2, "." + s + '/');
                } else {
                    file = new File(s1, '.' + s + '/');
                }
                break;

            case 4: // '\004'
                file = new File(s1, "Library/Application Support/" + s);
                break;

            default:
                file = new File(s1, s + '/');
                break;
        }
        if (!file.exists() && !file.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + file);
        } else {
            return file;
        }
    }

    private static EnumOS2 getOs() {
        String s = System.getProperty("os.name").toLowerCase();
        if (s.contains("win")) {
            return EnumOS2.windows;
        }
        if (s.contains("mac")) {
            return EnumOS2.macos;
        }
        if (s.contains("solaris")) {
            return EnumOS2.solaris;
        }
        if (s.contains("sunos")) {
            return EnumOS2.solaris;
        }
        if (s.contains("linux")) {
            return EnumOS2.linux;
        }
        if (s.contains("unix")) {
            return EnumOS2.linux;
        } else {
            return EnumOS2.unknown;
        }
    }

    public static boolean isServer() {
        try {
            Class.forName("net.minecraft.server.MinecraftServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
