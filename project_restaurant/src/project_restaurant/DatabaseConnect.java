package project_restaurant;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnect {
	private final String url = "jdbc:postgresql://localhost/web_db";
	private final String user = "webuser";
	private final String password = "postgres";
	
	private void connect() {
		try(Connection connection = DriverManager.getConnection(url,user,password);){
			if(connection!=null) {
				System.out.println("Connected to PostgreSQL server successfully...");
			}else {
				System.out.println("Failed to connect PostgreSQL server...");
			}
		
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	 public void executeQuery() {
	        String query = "SELECT * FROM Cliente";

	        try (Connection connection = DriverManager.getConnection(url, user, password);
	             Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query)) {

	            // Elabora il risultato della query
	            while (resultSet.next()) {
	                // Esempio di lettura delle colonne del risultato
	                int id = resultSet.getInt("cliente_id");
	                String nome = resultSet.getString("nome");
	                // E così via per le altre colonne
	                System.out.println("ID: " + id + ", Nome: " + nome);
	            }
	        } catch (SQLException e) {
	            // Gestione dell'eccezione
	            System.err.println("Error executing query:");
	            e.printStackTrace();
	        }
	    }
	
	public static void main(String[] args) {
		DatabaseConnect sqlConnect = new DatabaseConnect();
		sqlConnect.connect();
		
		DatabaseConnect databaseQuery = new DatabaseConnect();
        databaseQuery.executeQuery();
		
		
	}

}
