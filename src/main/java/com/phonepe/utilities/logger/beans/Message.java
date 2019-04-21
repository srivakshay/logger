package com.phonepe.utilities.logger.beans;

public class Message {
    private String content;
    private String level;
    private String callingClass;
    private String methodName;
    private int lineNumber;
    private String timestamp;

    public Message(String content, String level, String callingClass, String methodName, int lineNumber, String timestamp) {
        this.content = content;
        this.level = level;
        this.callingClass = callingClass;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public String getLevel() {
        return level;
    }

    public String getCallingClass() {
        return callingClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
