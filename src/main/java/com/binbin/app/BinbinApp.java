package com.binbin.app;

import com.binbin._enum.EmLanguageType;
import com.binbin._enum.EmToolsType;
import com.binbin.tools.CalcNumApp;
import com.binbin.utils.StringUtil;

import java.util.Map;
import java.util.Scanner;

public class BinbinApp {

    public static void main(String[] args) {
        Scanner in;
        int type = 0;
        System.out.println("select language please: ");
        System.out.println("1. English          2. 中文简体");
        while (true) {
            in = new Scanner(System.in);
            String numStr = in.nextLine();
            if (String.valueOf(EmLanguageType.ENGLISH.getCode()).equals(numStr.trim())) {
                type = EmLanguageType.ENGLISH.getCode();
                break;
            } else if (String.valueOf(EmLanguageType.CHINESE.getCode()).equals(numStr.trim())) {
                type = EmLanguageType.CHINESE.getCode();
                break;
            } else {
                System.out.println("please try again !");
            }
        }
        welcome(type);

        // 重试机会
        int errorCount = 3;
        listChoice(type);
        Map<Integer, String> listEnumMap = EmToolsType.listEnum();
        listEnumMap.forEach( (key, value) -> {
            System.out.println(key + ". " + value + "; ");
        });
        makeChoice(type);
        while (true) {
            in = new Scanner(System.in);
            String numStr = in.nextLine();
            if (StringUtil.isEmpty(numStr.trim())) {
                System.out.println("can't be '' ! \n");
                continue;
            }
            if ("exit".equals(numStr.trim().toLowerCase())
                    || "bye".equals(numStr.trim().toLowerCase())
                    || "see you".equals(numStr.trim().toLowerCase()) ) {
                exitLog(type);
                return;
            }
            int num = 0;
            try {
                num = Integer.valueOf(numStr);
            } catch (Exception e) {
                System.out.println("请输入数字! ");
                if (errorCount > 0) {
                    System.out.println("您还有" + errorCount + "次机会! ");
                } else {
                    System.out.println("系统强制退出");
                    System.exit(0);
                }
                errorCount--;
                continue;
            }

            if (EmToolsType.CALC_PHONE_USE_TIME.getCode() == num) {
//                CalcNumApp
                CalcNumApp.calcPhoneUseTime();
                return;
            } else if (EmToolsType.EXIT.getCode() == num) {
                exitLog(type);
                return;
            }

        }

    }

    private static void listChoice(int type) {
        if (type == EmLanguageType.ENGLISH.getCode()) {
            System.out.println("this is binbin's tools : \n");
        }
        if (type == EmLanguageType.CHINESE.getCode()) {
            System.out.println("请选择工具: \n");
        }
    }

    private static void makeChoice(int type) {
        if (type == EmLanguageType.ENGLISH.getCode()) {
            System.out.print("please make your choice: \n");
        }
        if (type == EmLanguageType.CHINESE.getCode()) {
            System.out.println("请输入您的选择: ");
        }
    }

    private static void welcome(int type) {
        if (type == EmLanguageType.ENGLISH.getCode()) {
            System.out.println("****** welcome to binbin tools 1.0 ******\n");
        }
        if (type == EmLanguageType.CHINESE.getCode()) {
            System.out.println("******    欢迎来到彬彬工具箱 1.0  ******\n");
        }
    }

    private static void exitLog(int type) {
        if (type == EmLanguageType.ENGLISH.getCode()) {
            System.out.println("see you, bye~\n");
        }
        if (type == EmLanguageType.CHINESE.getCode()) {
            System.out.println("再见~\n");
        }
    }
}
