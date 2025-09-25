package edu.ccrm.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigSingleton {
    private static ConfigSingleton instance;
    private Properties properties;

    private ConfigSingleton() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Config file not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConfigSingleton getInstance() {
        if (instance == null) {
            instance = new ConfigSingleton();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
