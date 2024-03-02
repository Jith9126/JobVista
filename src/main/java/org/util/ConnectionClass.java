package org.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
	private Connection conn;
	private static ConnectionClass currConnection;
	
	private ConnectionClass(){
		
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Recruitment","ragavi-zstk352","Karagavi3/"
        );
		}catch (SQLException e) {
			System.out.println("Problem in sql Connection");
		}catch (ClassNotFoundException e) {
			System.out.println("Problem in Sql jar");
		}
		
	}
	
	
	public static ConnectionClass CreateCon(){
		if(currConnection == null) {
			currConnection = new ConnectionClass();
		}
		return currConnection;
		
	}
	
	public Connection getConnection() {
		return conn;
	}
	
}
