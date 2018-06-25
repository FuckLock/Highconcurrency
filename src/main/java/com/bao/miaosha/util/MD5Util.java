package com.bao.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class MD5Util {

    private static final String DEFAULT_SALT = "1a2b3c4d";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassToFormPass(String inputPass) {
        String str = new StringBuilder().append(DEFAULT_SALT.charAt(0))
                    .append(DEFAULT_SALT.charAt(2)).append(inputPass)
                    .append(DEFAULT_SALT.charAt(5)).append(DEFAULT_SALT.charAt(4)).toString();
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        if (StringUtils.isBlank(salt)){
            return inputPassToFormPass(formPass);
        }
        String str = new StringBuilder().append(salt.charAt(0))
                    .append(salt.charAt(2)).append(formPass)
                    .append(salt.charAt(5)).append(salt.charAt(4)).toString();
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.md5("bao"));
        String a = MD5Util.inputPassToFormPass("bao");
        System.out.println(MD5Util.formPassToDBPass(a, DEFAULT_SALT));
        System.out.println("  ".charAt(0));
    }
}
