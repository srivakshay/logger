package com.phonepe.utilities.logger.enums;

public enum Levels {
    DEBUG("DEBUG", 900),
    INFO("INFO", 800),
    WARN("WARN", 700),
    ERROR("ERROR", 600),
    FATAL("FATAL", 500);

    private final String name;
    private final Integer level;

    public String getName() {
        return name;
    }

    private Levels(String name, Integer level) {
        this.level = level;
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }
}
