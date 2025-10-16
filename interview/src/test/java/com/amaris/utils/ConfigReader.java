package com.amaris.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {
    private static final Properties props = new Properties();

    static {
        try {
            // ✅ Read properties file explicitly as UTF-8
            props.load(Files.newBufferedReader(
                Paths.get("src/test/java/com/amaris/resources/testdata.properties"),
                StandardCharsets.UTF_8
            ));
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load testdata.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
