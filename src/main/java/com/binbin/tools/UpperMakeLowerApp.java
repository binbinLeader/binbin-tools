package com.binbin.tools;

import com.binbin.utils.StringUtil;

public class UpperMakeLowerApp {

    public static final char UNDERLINE = '_';

    public static void main(String[] args) {
        String[] strArr = {
                "divisionId",
                "caseId",
                "address",
                "unitPrice",
                "totalPrice",
                "queryDate",
                "avgUnitPrice",
                "avgPrice",
                "maxPrice",
                "minPrice",
                "priceRingRate",
                "priceYearRate",
                "mangerPrice",
                "liveness",
                "totalCellNumber",
                "queryCount",
                "returnCode",
                "remark",
                "endDate",
                "constructionName",
                "constructionAlias",
                "buildingName",
                "houseName",
                "propertyType",
                "quotationCount"
        };
        for (String str : strArr) {
            String result = camelToUnderline(str);
            System.out.println(result);
        }
    }

    /**
     * 驼峰转下划线
     * @param model
     * @return
     */
    public static String camelToUnderline(String model) {
        if (StringUtil.isEmpty(model)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < model.length(); i++) {
            char c = model.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     * @param underlineWord
     * @return
     */
    public static String underlineToCamel(String underlineWord) {
        if (StringUtil.isEmpty(underlineWord)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int length = underlineWord.length();
        for (int i = 0; i < length; i++) {
            char c = underlineWord.charAt(i);
            if (UNDERLINE == c) {
                if (++i < length) {
                    sb.append(Character.toUpperCase(underlineWord.charAt(i)));
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }










}
