package com.phonepe.utilities.logger;

import com.phonepe.utilities.logger.util.LogManager;

public class LoggerApplication {

    private static LogManager logManager;


    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        testMethod();
    }

    public static void testMethod() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        LogManager logManager = LogManager.getInstance();
        logManager.INFO("Info logging from test method");
    }
}
