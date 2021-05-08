package com.demo.restassure.helper;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class LoadConfig {
    public static final LoadConfig LOAD_CONFIG = getInstance();
    private final Config conf;

    private LoadConfig() {
        conf = ConfigFactory.load("application.conf");
    }

    public static LoadConfig getInstance() {
        return new LoadConfig();
    }

    public String getProperty(String key) {
        // Default environment is defined in pom.xml
        String chosenEnv = System.getProperty("environment");
        Config env = conf.getConfig(chosenEnv);
        return env.getString(key);
    }
}
