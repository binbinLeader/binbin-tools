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

        String fileStr = "D:/jiaobin/working/code/svn/Tasks/tags/USC_TASK_PAXT/etc/are.xml";
//        String filePath = fileStr.substring(fileStr.lastIndexOf("/") + 1);
        String filePath = fileStr.substring(0, fileStr.lastIndexOf("/") + 1);
        System.out.println(filePath);

        String pathStr = "/mnt/data_rd/user-data/xxyp/A088-XXYP/ZX12CW/receive/40/bak/202002/20200224/";
        String dateStr = pathStr.substring(pathStr.lastIndexOf("/") - 8, pathStr.lastIndexOf("/"));
        System.out.println(dateStr);
    }
}
