package com.phonepe.utilities.logger.sinks;

import com.phonepe.utilities.logger.beans.Message;

public abstract class AbstractSink {
    public AbstractSink() {
        configure();
    }

    public abstract void configure();

    public abstract void addMessage(Message message) throws Exception;
}
