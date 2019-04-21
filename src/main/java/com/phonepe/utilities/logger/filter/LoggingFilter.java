package com.phonepe.utilities.logger.filter;

import com.phonepe.utilities.logger.util.LevelManager;
import com.phonepe.utilities.logger.util.LogManager;

public class LoggingFilter {

    public static boolean messageToBeLogged(String level) {
        LevelManager levelManager = LevelManager.getInstance();
        return levelManager.getConfiguredLevels().containsKey(level);
    }
}
