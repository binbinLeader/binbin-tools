package com.binbin.tools;

public class BooleanTestApp {

    private static int num1 = 0;
    private static int num2 = 0;

    public static void main(String[] args) {
        if (getA() | getB()) {
            System.out.println("为true了");
        }
        System.out.println("num1=" + num1);
        System.out.println("num2=" + num2);
    }

    private static boolean getB() {
        num2 = 2;
        return true;
    }

    private static boolean getA() {
        num1 = 1;
        return false;
    }
}
