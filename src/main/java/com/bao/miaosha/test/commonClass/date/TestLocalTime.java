package com.bao.miaosha.test.commonClass.date;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;

public class TestLocalTime {

    public static void main(String[] args) {
        LocalTime localTime1 = LocalTime.now();
        System.out.println(localTime1);

        LocalTime localTime2 = LocalTime.of(5, 2, 1);
        LocalTime localTime3 = LocalTime.of(5, 2, 1,2);
        System.out.println(localTime2);
        System.out.println(localTime3);

        Time time = new Time(System.currentTimeMillis());
        System.out.println(time);
    }
}
