package com.binbin.tools;

import com.binbin.utils.DateUtils2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTestApp {

    public static void main(String[] args) throws ParseException {
//        String date = "2019-01-01 01:44:33";
//        changeFormatDate(date);
//        int days = DateUtils2.compareDate("2020/01/10", "2020/03/05");
//        System.out.println("相差:" + days + "天");

//        int days = DateUtils2.compareDate("20200110", "20200117", "yyyyMMdd");
//        System.out.println(days);
//
//        int days1 = DateUtils2.compareDate("20200117", "20200110", "yyyyMMdd");
//        System.out.println(days1);

//        String occurDate = DateUtils2.getRelativeDate("2020/03/31",0,0,1);
//        System.out.println(occurDate);
//        System.out.println(occurDate.compareTo("2020/03/27"));

//        String lastTaskDate = "2020/03/31";
//        String checkdate = "2020/03/27";
//        for (int i = 1; DateUtils2.getRelativeDate(lastTaskDate,0,0,i).compareTo(checkdate)<0; i++) {
//            String occurDate = DateUtils2.getRelativeDate(lastTaskDate,0,0,i);
//            System.out.println(occurDate);
//        }

//        String relativeMonth = DateUtils2.getRelativeDate(new Date(), 0, -1, 0);
//        System.out.println(relativeMonth);

//        String yesterday = DateUtils2.getYesterday();
//        System.out.println(yesterday);
        String theDayBeforeYesterday = DateUtils2.getTheDayBeforeYesterday();
        System.out.println(theDayBeforeYesterday);
    }



    private static void changeFormatDate(String date) {
        Date srcDate = null;
        try {
            srcDate = new SimpleDateFormat("yyyy/MM/dd").parse(date);
            String format = new SimpleDateFormat("yyyy/MM/dd").format(srcDate);
            System.out.println(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
