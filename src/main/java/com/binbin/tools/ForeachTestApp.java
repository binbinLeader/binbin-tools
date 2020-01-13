package com.binbin.tools;

public class ForeachTestApp {

    public static void main(String[] args) {
//        int i = 0;
//        while (i < 200000) {
//            System.out.println("i=" + (i+1) + "; i=" + (i+10000));
//            i=i+10000;
//        }
        for (int i = 60000; i < 80000; i=i+4000) {
            int lastNum = i + 4000;
            if (lastNum > 80000) {
                lastNum = 80000;
            }
            System.out.println("i=" + (i+1) + "; i=" + lastNum);
        }
    }
}
/*
i=1; i=30000        14283
i=30001; i=60000    44438
i=60001; i=90000    75286
i=90001; i=120000   105877
i=120001; i=150000  135572
i=150001; i=180000  164913
i=180001; i=200000  195119
 */