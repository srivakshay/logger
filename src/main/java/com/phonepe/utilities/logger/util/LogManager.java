package com.phonepe.utilities.logger.util;

import com.phonepe.utilities.logger.beans.Message;
import com.phonepe.utilities.logger.filter.LoggingFilter;
import com.phonepe.utilities.logger.sinks.AbstractSink;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

public class LogManager {

    private static LogManager INSTANCE;

    private LoadConfigurations loadConfigurations;

    private SinkLoaderFactory sinkLoaderFactory;

    private LogManager() {
        loadConfigurations = LoadConfigurations.getInstance();
        sinkLoaderFactory = new SinkLoaderFactory();
    }

    public synchronized static LogManager getInstance() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (INSTANCE == null)
            INSTANCE = new LogManager();
        return INSTANCE;
    }

    public void INFO(String msg) {
        writeLogs(msg, "INFO");
    }

    public void TRACE(String msg) {
        writeLogs(msg, "TRACE");
    }

    public void DEBUG(String msg) {
        writeLogs(msg, "DEBUG");
    }

    public void WARN(String msg) {
        writeLogs(msg, "WARN");
    }

    public void ERROR(String msg) {
        writeLogs(msg, "ERROR");
    }

    public void FATAL(String msg) {
        writeLogs(msg, "FATAL");
    }

    private String getCurrentDateTime() {
        String format = loadConfigurations.getConfiguration().get("ts_format");
        LocalDateTime localDateTime = LocalDateTime.now();
        return DateTimeFormatter.ofPattern(format, Locale.ENGLISH).format(localDateTime);
    }

    private void writeLogs(String msg, String level) {
        try {
            if (LoggingFilter.messageToBeLogged(level)) {
                SinkManager sinkManager = SinkManager.getInstance();
                Message message = new Message(msg, level, Thread.currentThread().getStackTrace()[3].getClassName(), Thread.currentThread().getStackTrace()[3].getMethodName(), Thread.currentThread().getStackTrace()[3].getLineNumber(), getCurrentDateTime());
                if (sinkManager.isMultithreaded()) {
                    ExecutorService executorService = sinkManager.getExecutorService();
                    Runnable runnable = () -> {
                        try {
                            writeToSink(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    };
                    executorService.submit(runnable);
                } else {
                    writeToSink(message);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToSink(Message message) throws Exception {
        AbstractSink abstractSink = sinkLoaderFactory.getSink();
        abstractSink.addMessage(message);
    }

}
