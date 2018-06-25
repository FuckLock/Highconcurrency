package com.bao.miaosha.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestZhenZhe {
    public static void main(String[] args) {
        String str = "aaaab";
        String regex = "a*b";
        boolean blue1 = str.matches(regex);
        System.out.println(blue1);

        Pattern pattern = Pattern.compile("a*b");
        String str1 = "aaaab";
        Matcher matcher = pattern.matcher(str1);
        boolean blue2 = matcher.matches();
        System.out.println(blue2);

        Pattern pattern1 = Pattern.compile("\\b\\w{3}\\b");
        String str2 = "aab aabb aab aasdfsd, ni zah womengs erf";
        Matcher matcher1 = pattern1.matcher(str2);
        while (matcher1.find()){
            System.out.println(matcher1.group());
        }
    }
}
