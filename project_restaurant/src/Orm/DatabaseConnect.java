package Orm;

import java.sql.Statement;

import domain_model.Customer;
import domain_model.Food;
import domain_model.Reservation;
import domain_model.Table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	//registra nuovo cliente 
	public boolean singIn(String cognome,String nome,String telefono) {
	    // Definiamo la query SQL per inserire un nuovo cliente nel database
		String query = "INSERT INTO Cliente (cognome, nome, telefono) VALUES (?, ?, ?)";
		
        try (Connection connection = DriverManager.getConnection(url, user, this.password);
             PreparedStatement statement = connection.prepareStatement(query)) {
        	// Impostiamo i valori dei parametri della query utilizzando setString
            // Il primo parametro (1, 2, 3) corrisponde all'ordine dei punti interrogativi nella query
            // Il secondo parametro è il valore del parametro che vogliamo inserire nella query
            statement.setString(1, cognome);
            statement.setString(2, nome);
            statement.setString(3, telefono);
            
            // Eseguiamo la query di inserimento e otteniamo il numero di righe inserite
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Se è stato inserito almeno un record, la registrazione è avvenuta con successo
        } catch (SQLException e) {        
        	// In caso di eccezione durante l'esecuzione della query, stampiamo l'errore
            e.printStackTrace();
            // Restituisci false per indicare che la registrazione non è avvenuta con successo
            return false;
        }
	}
	
	
	
	public boolean createReservation(Reservation r, Customer c) {
		String query = "INSERT INTO Prenotazione(data_evento, ora_evento, costo_totale, n_persone, note, cliente_id) VALUES (?,?,?,?,?,?)";
		
		try (Connection connection = DriverManager.getConnection(url, user, this.password);
	             PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1,r.getDate().getDayOfMonth()+"/"+r.getDate().getMonthValue()+"/"+r.getDate().getYear() );
			statement.setString(2,r.getDate().getHour()+":"+r.getDate().getMinute());
			statement.setString(3,null);
			statement.setInt(4,r.getNumberOfPerson() );
			statement.setString(5,r.getSpecialRequest());
			statement.setString(6,c.getId());
			
			  int rowsInserted = statement.executeUpdate();
	          return rowsInserted > 0;
		} catch (SQLException e) {        
        	// In caso di eccezione durante l'esecuzione della query, stampiamo l'errore
            e.printStackTrace();
            // Restituisci false per indicare che la registrazione non è avvenuta con successo
            return false;
        }
	}
	
	
	public boolean checkReservation(Reservation r) {
		//TODO:parametro reservation verificcare se sia diponibile un tavolo
		//a quella data per quelle persone
		
		return false;
	}
	
	//crea una conferma/ordine
	public boolean createOrder(Reservation r, Table t) {
		String query = "INSERT INTO Conferme(prenotazione, tavolo) VALUES (?,?)";
		try (Connection connection = DriverManager.getConnection(url, user, this.password);
	             PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, r.getId());
			statement.setInt(2, t.getNumber());
			 int rowsInserted = statement.executeUpdate();
	         return rowsInserted > 0;
	           
		} catch (SQLException e) {        
       	// In caso di eccezione durante l'esecuzione della query, stampiamo l'errore
           e.printStackTrace();
           // Restituisci false per indicare che la registrazione non è avvenuta con successo
           return false;
       }
	}
	
	public boolean deleteOrder(Reservation r, Table t) {
		String query = "DELETE FROM Conferme WHERE prenotazione = ? AND tavolo = ?";
		try (Connection connection = DriverManager.getConnection(url, user, this.password);
	             PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, r.getId());
			statement.setInt(2, t.getNumber());
			 int rowsInserted = statement.executeUpdate();
	         return rowsInserted > 0;
	           
		} catch (SQLException e) {        
       	// In caso di eccezione durante l'esecuzione della query, stampiamo l'errore
           e.printStackTrace();
           // Restituisci false per indicare che la registrazione non è avvenuta con successo
           return false;
       }
	}
	
	//metodo per visualizzare il menu
	public void viewMenu() {
		String query = "SELECT piatto, prezzo, descrizione FROM Menù";
		try (Connection connection = DriverManager.getConnection(url, user, password);
		         Statement statement = connection.createStatement();
		         ResultSet resultSet = statement.executeQuery(query)) {
		        
		        System.out.println("  Piatto                        | Prezzo | Descrizione");
		        System.out.println("--------------------------------|--------|------------------------------------");

		        while (resultSet.next()) {
		            String piatto = resultSet.getString("piatto");
		            double prezzo = resultSet.getDouble("prezzo");
		            String descrizione = resultSet.getString("descrizione");
		            
		            System.out.printf("  %-30s| %-7.2f| %-35s%n", piatto, prezzo, descrizione);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
	

	public String getUrl() {
		return url;
	}


	public String getUser() {
		return user;
	}


	public String getPassword() {
		return password;
	}
}
