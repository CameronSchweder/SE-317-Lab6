package lab6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMySQLConnection {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/atm_system";
		String user = "projectuser";
		String password = "projectpass123";
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			System.out.println("Connection successful!");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
