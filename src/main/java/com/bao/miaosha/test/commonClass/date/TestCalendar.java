package com.bao.miaosha.test.commonClass.date;

import java.util.Calendar;

public class TestCalendar {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month =calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        System.out.println(year + "年" + month + "月" + date + "日");

        // 5年后十天前
        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.YEAR, 5);
        calendar2.add(Calendar.DATE, -10);
        year = calendar2.get(Calendar.YEAR);
        month =calendar2.get(Calendar.MONTH);
        date = calendar2.get(Calendar.DATE);
        System.out.println(year + "年" + month + "月" + date + "日");

        // 3年前
        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.YEAR, -3);
        year = calendar3.get(Calendar.YEAR);
        month =calendar3.get(Calendar.MONTH);
        date = calendar3.get(Calendar.DATE);
        System.out.println(year + "年" + month + "月" + date + "日");

        Calendar calendar4 = Calendar.getInstance();
        calendar4.set(2011, 11, 12);
        year = calendar4.get(Calendar.YEAR);
        month =calendar4.get(Calendar.MONTH);
        date = calendar4.get(Calendar.DATE);
        System.out.println(year + "年" + month + "月" + date + "日");

    }
}
