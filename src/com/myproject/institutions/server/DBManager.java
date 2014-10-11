package com.myproject.institutions.server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.myproject.institutions.server.DBManager;


public class DBManager {
	
	private static Connection conn;
	private static DBManager instance = new DBManager();
	
	private static final String USER = "root";
	private static final String PASSWD = "root";
	private static final String DB_NAME = "test";
	
	private DBManager(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME, USER, PASSWD);
			
		} catch (SQLException sqle){
			System.err.println(sqle);
			sqle.printStackTrace();
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		return conn;
	}
	
}
