package com.dcpro.demo.controller.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	//�����������ݿ����ӵķ���
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/dc?characterEncoding=utf-8", "root", "970730");
		return connection;
	
	}
	//�ر����ݿ����ӵķ���
	
	public static void close(Statement stmt ,Connection con) throws SQLException {
		if (stmt != null) {
			stmt.close();
		}
		if (con != null) {
			con.close();
		}
	}
	
	public static void close(ResultSet rs, Statement stmt ,Connection con) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		close(stmt, con);
	}
}
