package com.bao.miaosha.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProperUtil {

    private static Properties prop;
    private static Logger logger = LoggerFactory.getLogger(ProperUtil.class);

    static {
        try {
            prop = new Properties();
            prop.load(ProperUtil.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            logger.error("prop.load异常 {}", e);
        }
    }

    public static String getProperty(String key){
        return prop.getProperty(key.trim());
    }

    public static void main(String[] args) {
        System.out.println(ProperUtil.prop.getProperty("redis.host"));
    }

}
