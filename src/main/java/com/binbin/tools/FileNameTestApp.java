package com.binbin.tools;

public class FileNameTestApp {

    public static void main(String[] args) {
        String fileName = "ZX12CW_ZX12CW2710230297950432296966_10_001.pdf";
        String substring = fileName.substring(0, fileName.lastIndexOf("."));
        String sufferName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(substring);
        System.out.println(sufferName);


        String yearMonth = "20190101";
        System.out.println(yearMonth.substring(0, 6));
    }
}
