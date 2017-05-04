package com.it;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8";
	// MySQL配置时的用户名
	private static final String USER = "root";
	// Java连接MySQL配置时的密码
	private static final String PASSWORD= "root";
	private Statement statement;
	private Connection conn;

	public DB() {
		// 加载驱动程序
		try {
			Class.forName(DRIVER);
			// 连续数据库
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Succeeded connecting to the Database!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 验证用户名是否存在
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public boolean isExits(String name) {
		try {
			// 要执行的SQL语句
			String sql = "select * from user";
			statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				// System.out.println(rs.getString("username"));
				if (rs.getString("username").equals(name)) {
					return true;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 验证用户名和密码是否正确
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public boolean isLogin(String name, String password) {
		try {
			// 要执行的SQL语句

			String sql = "select * from user";
			statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {

				// System.out.println(rs.getString("username"));
				if (rs.getString("username").equals(name)
						&& rs.getString("password").equals(password)) {
					return true;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertuser(String name, String password) {
		String insert = "insert into user(username,password)values('" + name
				+ "','" + password + "')";
		try {
			statement = conn.createStatement();
			statement.executeUpdate(insert);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}
}