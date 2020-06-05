package com.binbin.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarTestApp {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

        Calendar now = Calendar.getInstance();
//        System.out.println(sdf.format(now.getTime()));
//        Calendar time1 = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 16);
//
//        System.out.println(sdf.format(time1.getTime()));
//        Calendar time2 = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, 15);
//        System.out.println(sdf.format(time2.getTime()));
//
//        if (now.after(time1) && now.before(time2)) {
//            now.add(Calendar.MONTH, -1);
//        } else {
//            now.add(Calendar.MONTH, -2);
//        }
//        System.out.println("-----------------------");
//        System.out.println(sdf.format(now.getTime()));
//        System.out.println(now.get(Calendar.MONTH));
        System.out.println(now.get(Calendar.DATE));
//        System.out.println(now.get(Calendar.YEAR));
//        int month = now.get(Calendar.MONTH) + 1;
//        if (month == 1 || month == 2 || month == 3) {
//            System.out.println("一季度");
//        } else if (month == 4 || month == 5 || month == 6) {
//            System.out.println("二季度");
//        } else if (month == 7 || month == 8 || month == 9) {
//            System.out.println("三季度");
//        } else if (month == 10 || month == 11 || month == 12) {
//            System.out.println("四季度");
//        }
    }
}
