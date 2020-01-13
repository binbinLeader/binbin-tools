package com.binbin.tools;

import com.binbin.utils.DateUtils2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTestApp {

    public static void main(String[] args) {
//        String date = "2019-01-01 01:44:33";
//        changeFormatDate(date);
        int days = DateUtils2.compareDate("2020/01/10", "2020/03/05");
        System.out.println("相差:" + days + "天");
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
