package com.bao.miaosha.util;

import java.util.regex.Pattern;

public class ValidateUtil {

    public static boolean isMoblie(String mobile){
        String regex = "1\\d{10}";
        return mobile.matches(regex);
    }

    public static boolean isMoblie2(String mobile){
        Pattern pattern = Pattern.compile("1\\d{10}");
        return pattern.matcher(mobile).matches();
    }
}
