package com.bao.miaosha.test;

import com.bao.miaosha.util.MD5Util;

public class TestMd5 {
    public static void main(String[] args) {
        String password = MD5Util.formPassToDBPass("bao", "1a2b3c4d");

        String dbPass = MD5Util.formPassToDBPass(password, "1a2b3c4d");
        System.out.println(dbPass);
    }
}
