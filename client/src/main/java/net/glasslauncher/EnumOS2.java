package net.glasslauncher;

public enum EnumOS2 {
    linux("linux", 0),
    solaris("solaris", 1),
    windows("windows", 2),
    macos("macos", 3),
    unknown("unknown", 4);

    static final EnumOS2[] allOSes;

    static {
        allOSes = (new EnumOS2[]{
                linux, solaris, windows, macos, unknown
        });
    }

    EnumOS2(String s, int i) {
    }
}
