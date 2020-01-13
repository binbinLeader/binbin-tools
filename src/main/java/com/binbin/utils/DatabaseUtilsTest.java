package com.binbin.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtilsTest {

	private DatabaseUtilsTest() {

	}

	private static volatile DatabaseUtilsTest instance;

	public static DatabaseUtilsTest getIstance() {
		// 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
		if (instance == null) {
			// 同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
			synchronized (DatabaseUtilsTest.class) {
				// 未初始化，则初始instance变量
				if (instance == null) {
					instance = new DatabaseUtilsTest();
				}
			}
		}
		return instance;
	}

	public Connection getConnection(String driver, String url, String username, String password) {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(false);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public Connection getConnection(String url, String username, String password) {
		String driver = "com.mysql.jdbc.Driver";
		return getConnection(driver, url, username, password);
	}

	public Connection getConnection(String url) {
		return getConnection(url, "bhxt_ams_user", "bhxt_ams_pass0512");
	}

	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			}
			catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public static void close(Connection conn, ResultSet rs, Statement stmt) {
		close(rs);
		close(stmt);
		close(conn);
	}
	
	public static void close(Connection conn, Statement stmt) {		
		close(stmt);
		close(conn);
	}

	public static void close(ResultSet rs, Statement stmt) {
		close(rs);
		close(stmt);
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
