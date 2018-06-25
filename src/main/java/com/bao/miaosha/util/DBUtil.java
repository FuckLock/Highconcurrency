package com.bao.miaosha.util;

import com.bao.miaosha.test.jdbc.DateBaseUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

public class DBUtil {

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(DateBaseUtil.class.getClassLoader().getResourceAsStream("application.properties"), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static Connection connection(){
        String username = properties.getProperty("spring.datasource.username");
        String password = properties.getProperty("spring.datasource.password");
        String url = properties.getProperty("spring.datasource.url");
        String driver = properties.getProperty("spring.datasource.driverClassName");

        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

}
