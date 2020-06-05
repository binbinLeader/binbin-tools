package com.binbin.tools;

import com.binbin.utils.StringUtil;

public class UpperMakeLowerApp {

    public static final char UNDERLINE = '_';

    public static void main(String[] args) {
        String[] strArr = {
/*
*/
"contractCode",
"name",
"idType",
"idNum",
"creditLimType",
"limLoopFlg",
"creditLim",
"cy",
"conEffDate",
"conExpDate",
"conStatus",
"creditRest",
"creditRestCode",
"managerOrgId",
"managerUserId",
"operOrgId",
"operUserId",
"updateDate",
"occurDate",
"extend1",
"extend2",
"extend3",





        };
        for (String str : strArr) {
            String result = camelToUnderline(str);
            System.out.println(result);
        }
    }

    // 建表语句
    /*
create table ICR_INT_CREDITINFO
(
    serialno        VARCHAR2(64) not null,



    created_time       VARCHAR2(20),
    update_time        VARCHAR2(20),
    flag1              VARCHAR2(255),
    flag2              VARCHAR2(255),
    flag3              VARCHAR2(255),
    constraint PK_icr_int_creditinfo primary key (serialno)
);
comment on table icr_int_creditinfo is
'个人授信协议信息表';
comment on column icr_int_creditinfo.serialno is
'主键';



comment on column icr_int_creditinfo.created_time
  is '创建时间';
comment on column icr_int_creditinfo.update_time
  is '修改时间';
comment on column icr_int_creditinfo.flag1
  is '备注1';
comment on column icr_int_creditinfo.flag2
  is '备注2';
comment on column icr_int_creditinfo.flag3
  is '备注3';
     */

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
