package com.bao.miaosha.test.commonClass.date;

import java.sql.Time;

public class TestTime {
    public static void main(String[] args) {
        Time time = new Time(System.currentTimeMillis());
        System.out.println(time);
    }
}
