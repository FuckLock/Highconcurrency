package com.bao.miaosha.test.commonClass.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class TestLocalDate {
    public static void main(String[] args) {
        /**
         * 对比Date  calendar 和 LocalDate(1.8出现)
         */
        LocalDate localDate1 = LocalDate.now();
        System.out.println(localDate1);

        LocalDate localDate2 = LocalDate.of(2012, 12, 12);
        String str = String.valueOf(localDate2);
        System.out.println(str);


        LocalDate localDate3 = LocalDate.of(2015, 11, 12);
        System.out.println(localDate3.getYear());
        System.out.println(localDate3.getMonthValue());
        System.out.println(localDate3.getDayOfMonth());

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormat1 = simpleDateFormat.format(date1);
        System.out.println(date1);
        System.out.println(dataFormat1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));

    }
}
