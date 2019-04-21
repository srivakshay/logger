package com.phonepe.utilities.logger.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LoadConfigurations {

    private LoadConfigurations() {
        loadUserConfig();
    }

    private static LoadConfigurations INSTANCE;
    private Map<String, String> configuration; //configuration defined by user

    public static synchronized LoadConfigurations getInstance() {
        if (INSTANCE == null)
            INSTANCE = new LoadConfigurations();
        return INSTANCE;
    }


    private void loadUserConfig() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("logger.properties")) {
            if (inputStream != null) {
                Properties properties = new Properties();
                properties.load(inputStream);
                configuration = new HashMap<String, String>((Map) properties);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

}
