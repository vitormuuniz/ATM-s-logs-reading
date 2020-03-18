package databaseOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
	
	// init database constants
	private static final String DATABASE_DRIVER = "org.postgresql.Driver";
	private static final String DATABASE_URL_LOCAL = "jdbc:postgresql://localhost/logs?user=postgres&password=root&ssl=false";
		private static final String DATABASE_URL_SERVER = "jdbc:postgresql://192.168.14.71/postgres?user=desafioacc01&password=pwddesafioacc01";

	// init connection object
	private static Connection connection;
	
	// connect database
	public static Connection connect() {
	    if (connection == null) {
	        try {
	            Class.forName(DATABASE_DRIVER);
	            connection = DriverManager.getConnection(DATABASE_URL_SERVER);
	            System.out.println("\tConnection opened");
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return connection;
	}
	
	// disconnect database
	public static void disconnect() {
	    if (connection != null) {
	        try {
	            connection.close();
	            connection = null;
	            System.out.println("\tConnection closed");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}