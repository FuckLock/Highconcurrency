package com.bao.miaosha.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

public class IOUtil {

    private static Logger logger = LoggerFactory.getLogger(IOUtil.class);

    public static <T extends Closeable> void close(T ... ios){
        for (Closeable io:ios){
            if (io != null){
                try {
                    io.close();
                } catch (IOException e) {
                    logger.error("关闭IO异常", e);
                }
            }
        }
    }

}
