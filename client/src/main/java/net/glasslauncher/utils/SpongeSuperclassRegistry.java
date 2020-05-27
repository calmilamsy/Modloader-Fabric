package net.glasslauncher.utils;

import com.google.common.collect.Maps;

import java.util.Map;

public class SpongeSuperclassRegistry {

    private static Map<String, String> classSuperclassMap = Maps.newHashMap();

    public static void registerSuperclassModification(String targetClass, String newSuperClass) {
        if (classSuperclassMap.containsKey(targetClass)) {
            throw new IllegalArgumentException(String.format("Superclass '%s' already registered for class '%s'!", newSuperClass, targetClass));
        }
        classSuperclassMap.put(targetClass, newSuperClass.replace('.', '/'));
    }

    public static String getSuperclass(String targetClass) {
        return classSuperclassMap.get(targetClass);
    }

}