package com.bao.miaosha.test;

import java.util.Date;

public class TestString {
    public static void main(String[] args) {
        String s2 = String.format("%1$tY-%1$tm-%1$te", new Date());
        System.out.println(s2);
        System.out.println(String.format("我的名字叫%s", "小明"));
        System.out.println(String.format("我叫%s,她叫%s", "小明","小方"));
        System.out.println(String.format("我叫%2$s,她叫%1$s", "小明","小方"));
        System.out.println(String.format("%o", 10));
        System.out.println(String.format("%x", 10));
    }
}
