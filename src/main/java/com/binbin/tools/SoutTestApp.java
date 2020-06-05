package com.binbin.tools;

public class SoutTestApp {

    public static void main(String[] args) {
//        int visitNumMultiappl = 99960;
//        int sussessNumMultiappl = 53694;
//        int visitNumSXZX = 99960;
//        int sussessNumSXZX = 1623;
//
//        System.out.println("多重风险检测接口-总共请求次数: " + visitNumMultiappl + " 次");
//        System.out.println("多重风险检测接口-命中次数: " + sussessNumMultiappl + " 次");
//        System.out.println("多重风险检测接口-未命中次数: " + (visitNumMultiappl - sussessNumMultiappl) + " 次");
//        System.out.println();
//        System.out.println("失信执行查询T2接口-总共请求次数: " + visitNumSXZX + " 次");
//        System.out.println("失信执行查询T2接口-命中次数: " + sussessNumSXZX + " 次");
//        System.out.println("失信执行查询T2接口-未命中次数: " + (visitNumSXZX - sussessNumSXZX) + " 次");

        String dsDate = "2020/03/22".replaceAll("/", "");
        String updateDsDate = "00" + dsDate.substring(2);
        System.out.println(updateDsDate);
    }
}
