package utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public final class Config {

    private static final Properties PROPS = System.getProperties();

    static {
        String path = "src/test/resources/endpoints.properties";
        try (InputStream in = Files.newInputStream(Paths.get(path))) {
            PROPS.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Cannot load config file: " + path);
        }
    }

    private Config() { }

    /** read‑only accessor */
    public static String get(String key) {
        return PROPS.getProperty(key);
    }
}
