package com.binbin.tools;

import com.alibaba.fastjson.JSON;
import com.binbin.model.Student;

public class DomainTestApp {

    public static void main(String[] args) {
        Student s1 = new Student();
        s1.setAge(10);
        s1.setName("一号");
        s1.setDesc("不打印这个");

        Student s2 = new Student();
        s2 = s1;
        s2.setDesc("");
        System.out.println("s1=" + JSON.toJSONString(s1));
        System.out.println("s2=" + JSON.toJSONString(s2));
    }

}
