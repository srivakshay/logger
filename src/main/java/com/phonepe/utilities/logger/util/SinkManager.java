package com.phonepe.utilities.logger.util;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SinkManager {

    private LevelManager levelManager;

    private LoadConfigurations loadConfigurations;

    private static SinkManager INSTANCE;

    private String definedSink;

    private String sinkClass;

    private boolean multithreaded;

    private String writeMode;

    private ExecutorService executorService;

    private SinkManager() {
        loadConfigurations = LoadConfigurations.getInstance();
        levelManager = LevelManager.getInstance();
        setSinkConfig();
        if (isMultithreaded()) {
            if (getWriteMode().equals("SYNC")) {
                executorService = Executors.newSingleThreadExecutor();
            } else {
                executorService = Executors.newFixedThreadPool(10);
            }
        }
    }

    public static synchronized SinkManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SinkManager();
        return INSTANCE;
    }

    private void setSinkConfig() {
        Map<String, String> configuration = loadConfigurations.getConfiguration();
        definedSink = configuration.get("sink_type");
        sinkClass = configuration.get("class");
    }

    public String getSinkClass() {
        return sinkClass;
    }

    public String getDefinedSink() {
        return definedSink;
    }

    public boolean isMultithreaded() {
        multithreaded = false;
        if (loadConfigurations.getConfiguration().containsKey("thread_model")) {
            if (loadConfigurations.getConfiguration().get("thread_model").equals("MULTI"))
                multithreaded = true;
        }
        return multithreaded;
    }

    public String getWriteMode() {
        if (isMultithreaded()) {
            if (loadConfigurations.getConfiguration().containsKey("write_mode")) {
                writeMode = loadConfigurations.getConfiguration().get("write_mode");
            } else {
                writeMode = "ASYNC";
            }
        }
        return writeMode;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
