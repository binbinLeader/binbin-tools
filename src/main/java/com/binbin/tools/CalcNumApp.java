package com.binbin.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalcNumApp {

    public static void main(String[] args) {
//        addNum(14,15,16,18,20,24,26,28,31,37,42,44,50,54,58,60,61,64,66,70);
/*,,,
 */
        addNum(32017,32193,32021,32054);
//        System.out.println(10 / 3);
    }

    public static void addNum(int ... ints) {
        int sum = 0;
        for (int num : ints) {
            sum += num;
        }
        System.out.println("总和=" + sum);
    }

    public static void calcPhoneUseTime() {
        List<Integer> timeList = new ArrayList<Integer>();
        Scanner in = new Scanner(System.in);
        String numStr = in.nextLine();

    }
}
