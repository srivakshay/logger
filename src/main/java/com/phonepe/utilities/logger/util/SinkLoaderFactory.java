package com.phonepe.utilities.logger.util;

import com.phonepe.utilities.logger.sinks.AbstractSink;
import com.phonepe.utilities.logger.sinks.FileSink;

import java.util.HashMap;
import java.util.Map;

public class SinkLoaderFactory {

    private Map<String, AbstractSink> sinkObjects;


    public AbstractSink getSink() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        SinkManager sinkManager = SinkManager.getInstance();
        String sinkType = sinkManager.getDefinedSink();
        String sinkClassName = sinkManager.getSinkClass();
        if (sinkObjects != null && sinkObjects.containsKey(sinkType))
            return sinkObjects.get(sinkType);
        else {
            sinkObjects = new HashMap<>();
            Class sinkClass = Class.forName(sinkClassName);
            sinkObjects.put(sinkClassName, (AbstractSink) sinkClass.newInstance());
            return (AbstractSink) sinkClass.newInstance();
        }
    }
}
