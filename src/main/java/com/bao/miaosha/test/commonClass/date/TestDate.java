package com.bao.miaosha.test.commonClass.date;

import java.util.Date;

public class TestDate {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);

        Date date1 = new Date(System.currentTimeMillis());
        System.out.println(date1);

        Date date2 = new Date(1000 * 60 * 60);
        System.out.println(date2);

        Date date3 = new Date();
        System.out.println(date3.getTime());
        System.out.println(System.currentTimeMillis());
    }
}
