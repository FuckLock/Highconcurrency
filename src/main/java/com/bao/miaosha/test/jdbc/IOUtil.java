package com.bao.miaosha.test.jdbc;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class IOUtil {

    public static void close(Closeable... closeables){
        for (Closeable io:closeables) {
            if (io != null){
                try {
                    io.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T extends Closeable> void closeAll(T ... closeables){
        for (Closeable io:closeables){
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
