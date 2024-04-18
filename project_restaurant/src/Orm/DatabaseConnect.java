package Orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
	private static final String url = "jdbc:postgresql://localhost/web_db";
	private static final String user = "webuser";
	private static final String password = "postgres";
	private static Connection connection = null;
	
	private DatabaseConnect() {}
	
	static public Connection getConnection() throws SQLException, ClassNotFoundException  {
		connection = DriverManager.getConnection(url,user,password);
		return connection;
		}

	}
