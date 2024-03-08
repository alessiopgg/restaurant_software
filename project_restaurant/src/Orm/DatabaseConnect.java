package Orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
	private static final String url = "jdbc:postgresql://localhost/web_db";
	private static final String user = "webuser";
	private static final String password = "postgres";
	private static Connection connection = null;
	
	static public Connection getConnection() throws SQLException, ClassNotFoundException  {
		Class.forName("org.postgresql.Driver");//carica dinamicamente il driver JDBC per PostgreSQL.
		//La classe Class viene utilizzata per caricare in memoria il driver specificato. Questo è 
		//necessario per poter utilizzare il driver JDBC per comunicare con il database.
		if(connection==null)
			connection=DriverManager.getConnection(url,user,password);
		return connection;
	}

}
