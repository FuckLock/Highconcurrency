package com.bao.miaosha.test.commonClass.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class TestLocalDateTime {

    public static void main(String[] args) {
        LocalDateTime dateTime1 = LocalDateTime.now();
        System.out.println(dateTime1);

        LocalDateTime dateTime2 = LocalDateTime.of(2014,12,3,4,5,6);
        System.out.println(dateTime2);

        LocalDateTime dateTime3 = LocalDateTime.now().plusDays(2);
        System.out.println(dateTime3);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        calendar.add(Calendar.DATE, 2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(calendar.getTime()));
    }
}
