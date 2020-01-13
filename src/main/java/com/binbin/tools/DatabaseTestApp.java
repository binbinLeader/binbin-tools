package com.binbin.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTestApp {

    public static void main(String[] args) {

        Connection con = null;
        Statement stmt = null;
        String jdbcDriver = "com.mysql.jdbc.Driver";
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/binbin_test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=true";
        String userName = "root";
        String pwd = "root";
        try {

            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbUrl, userName, pwd);
            stmt = con.createStatement();
            String sql = "SELECT * FROM usertb";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("userid");
                String name = rs.getString("name");
                System.out.println(id + "-" + name);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
