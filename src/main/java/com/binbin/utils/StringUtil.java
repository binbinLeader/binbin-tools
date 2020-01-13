package com.binbin.utils;

public class StringUtil {

    public static boolean isEmpty(String str) {
        return null == str || ("".equals(str.trim()));
    }
}
