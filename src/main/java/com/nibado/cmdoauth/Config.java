package com.nibado.cmdoauth;

import java.io.IOException;
import java.util.Properties;

public class Config {
    public static Properties get() {
        Properties properties = new Properties();

        try {
            properties.load(Config.class.getResourceAsStream("/secrets.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }
}
