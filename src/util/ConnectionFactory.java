package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	public Connection getConnection() {
		
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@192.168.0.75:1521:xe";
			String user = "hr";
			String password = "hr";
			conn = DriverManager.getConnection(url, user, password);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	public Connection getConn() {
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:this:@localhost:1521:xe";
			String user = "hr";
			String pwd = "hr";
			
			conn = DriverManager.getConnection(url, user, pwd);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
}

