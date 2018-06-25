package com.bao.miaosha.test.commonClass.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestDateFormat {

    public static void main(String[] args) throws ParseException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(date));

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat2.parse("2018-11-02 23:59:50"));
    }
}
