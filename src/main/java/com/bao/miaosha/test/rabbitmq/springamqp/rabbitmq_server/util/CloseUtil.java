package com.bao.miaosha.test.rabbitmq.springamqp.rabbitmq_server.util;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {

    public static <T extends Closeable> void close(T... ios){
        for (Closeable io:ios){
            if (io != null){
                try {
                    io.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
