package com.phonepe.utilities.logger.sinks;

import com.phonepe.utilities.logger.beans.Message;
import com.phonepe.utilities.logger.util.LoadConfigurations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Map;

public class DBSink extends AbstractSink {
    private Connection connection;

    @Override
    public void configure() {
        LoadConfigurations loadConfigurations = LoadConfigurations.getInstance();
        Map<String, String> config = loadConfigurations.getConfiguration();
        if (config.containsKey("dialect")) {
            if (config.get("dialect").equals("mysql")) {
                try {
                    String url = "jdbc:mysql://" + config.get("db_host") + ":" + config.get("db_port")+"/"+config.get("schema");
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection(url, config.get("username"), config.get("password"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addMessage(Message message) throws Exception {
        String sql="INSERT INTO log VALUES('"+message.getTimestamp()+"','"+message.getCallingClass()+"', '"+message.getMethodName()+"', "+message.getLineNumber()+", '"+message.getLevel()+"', '"+message.getContent()+"')";
        Statement statement=connection.createStatement();
        statement.execute(sql);
    }
}
