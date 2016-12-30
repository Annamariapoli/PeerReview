package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	public static Connection getConnection() throws SQLException{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		String jdbcURL = "jdbc:mysql://localhost:3306/porto?user=root&password"; 
		
		Connection conn = DriverManager.getConnection(jdbcURL);
		
		return conn;
	}


}
