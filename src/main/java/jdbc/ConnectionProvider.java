package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import excepciones.SQLExceptionCreated;

public class ConnectionProvider {
	
	private static String url = "jdbc:sqlite:turismo-tierra-media.db";
	private static Connection connection;
	
	public static Connection getConnection(){
		if(connection == null) {
			try {
				connection = DriverManager.getConnection(url);
			} catch (Exception e) {
				throw new SQLExceptionCreated(e);
			}
		}
		return connection;
	}

	public static void closeConnection() {
		try {
			connection.close();
		} catch (Exception e) {
			throw new SQLExceptionCreated(e);
		}
	}
}
