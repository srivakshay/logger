package com.phonepe.utilities.logger.util;

import com.phonepe.utilities.logger.enums.Levels;
import com.phonepe.utilities.logger.beans.LogLevel;

import java.util.*;
import java.util.stream.Collectors;

public class LevelManager {

    private LoadConfigurations loadConfigurations;

    private Map<String, Integer> logLevels;

    private Map<String, Integer> configuredLevels;

    private static LevelManager INSTANCE;

    private LevelManager() {
        loadConfigurations = LoadConfigurations.getInstance();
        setConfiguredLevels();
    }

    public static synchronized LevelManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new LevelManager();
        return INSTANCE;
    }

    private void setConfiguredLevels() {
        List<String> definedLevels = getAllDefinedLevels();
        logLevels = Arrays.stream(Levels.values()).collect(Collectors.toMap(Levels::getName, Levels::getLevel));
        List<LogLevel> definedLogLevels = definedLevels.stream().filter(level -> Objects.nonNull(logLevels.get(level))).map(level -> new LogLevel(level, logLevels.get(level))).collect(Collectors.toList());
        if(!definedLevels.isEmpty()) {
            LogLevel highestLevel = definedLogLevels.stream().max(Comparator.comparing(level -> level.getLevel())).get();
            configuredLevels = new HashMap<>();
            configuredLevels.putAll(logLevels);
            configuredLevels.entrySet().removeIf(e -> e.getValue() > highestLevel.getLevel());
        }
    }


    public List<String> getAllDefinedLevels() {
        String levels = loadConfigurations.getConfiguration().get("log_level");
        return Arrays.asList(levels.split(","));
    }

    public Map<String, Integer> getLogLevels() {
        return logLevels;
    }

    public Map<String, Integer> getConfiguredLevels() {
        return configuredLevels;
    }
}
