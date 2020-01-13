package com.binbin.tools;

import java.util.Random;

public class RandomApp {

    public static void main(String[] args) {
        Random random = new Random();
        for (int rowNum = 1; rowNum <= 10000; rowNum++) {
            int i = random.nextInt(100-50) + 50;
            if (i == 99) {
                System.out.println(i);
            }
        }
    }
}
