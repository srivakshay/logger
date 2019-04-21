package com.phonepe.utilities.logger.sinks;

import com.phonepe.utilities.logger.beans.Message;
import com.phonepe.utilities.logger.util.LoadConfigurations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FileSink extends AbstractSink {
    private String fileLocation;
    private String dateFormat;
    private String path;

    @Override
    public void configure() {
        LoadConfigurations loadConfigurations = LoadConfigurations.getInstance();
        Map<String, String> configuration = loadConfigurations.getConfiguration();
        fileLocation = configuration.get("file_location");
    }

    @Override
    public void addMessage(Message message) throws IOException {
        Path path = Paths.get(fileLocation);
        Files.createDirectories(path.getParent());
        if (!Files.exists(path))
            Files.createFile(path);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileLocation, true))) {
            bufferedWriter.write("\r\n["+message.getTimestamp()+"] "+message.getLevel()+" logged - Class: "+ message.getCallingClass()+", Method: "+message.getMethodName()+", Line: "+message.getLineNumber());
            bufferedWriter.write("\r\n["+message.getTimestamp()+"] Message: "+message.getContent());
        }
    }
}
