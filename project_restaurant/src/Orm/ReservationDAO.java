package Orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import domain_model.Customer;
import domain_model.Reservation;

public class ReservationDAO {

	public void insertReservation(Reservation r) throws ClassNotFoundException, SQLException {
		String query = "INSERT INTO Prenotazione(data_evento, ora_evento, n_persone, note, cliente_id) VALUES (?,?,?,?,?,?)";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1,r.getDate().getDayOfMonth() + "/" + r.getDate().getMonthValue() + "/" + r.getDate().getYear());
			statement.setString(2, r.getDate().getHour() + ":" + r.getDate().getMinute());
			statement.setInt(3, r.getNumberOfPerson());
			statement.setString(4, r.getSpecialRequest());
			statement.setString(5, r.getName());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteReservation(Reservation r)throws ClassNotFoundException, SQLException{
		String query = "DELETE FROM Prenotazione WHERE id_prenotazione = ?";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, r.getId());
			statement.executeUpdate();
			statement.close();
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}
	
	public boolean checkReservation(Reservation r)throws ClassNotFoundException, SQLException{
		String query = "SELECT id_tavolo, COUNT(*) AS num_tavoli_disponibili\r\n"
				+ "FROM Tavolo\r\n"
				+ "WHERE n_posti >= ? -- Numero di posti richiesto\r\n"
				+ "AND occupazione = false -- Il tavolo non è occupato\r\n"
				+ "AND id_tavolo NOT IN (\r\n"
				+ "    SELECT DISTINCT id_tavolo\r\n"
				+ "    FROM Prenotazione\r\n"
				+ "    WHERE data_evento = ? -- Data della prenotazione\r\n"
				+ "    AND ora_evento BETWEEN ? AND ? -- Ora della prenotazione e tre ore dopo\r\n"
				+ ")\r\n"
				+ "GROUP BY id_tavolo";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, r.getNumberOfPerson());
			statement.setString(2, r.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			statement.setString(3, r.getDate().toString()); // Impostare l'ora come stringa nel formato "HH:mm"
		    statement.setString(4, r.getDate().plusHours(3).toLocalTime().toString()); // Impostare l'ora dopo tre ore
		    ResultSet rs = statement.executeQuery();
		    while (rs.next()) {
	            int num_tavoli_disponibili = rs.getInt("num_tavoli_disponibili");
	            // Verifica se il numero di tavoli disponibili è maggiore di zero
	            if (num_tavoli_disponibili > 0) {
	                // Tavoli disponibili trovati, la prenotazione può essere completata
	                return true;
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        
	    }
	    
	    // Nessun tavolo disponibile trovato che soddisfi i requisiti della prenotazione
	    return false;

			
		}
	
	
	public ArrayList<Reservation> getDailyReservation(LocalDateTime day)throws ClassNotFoundException, SQLException{
		ArrayList<Reservation> dailyReservation= new ArrayList<>();
		String formattedDate = day.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String query = "SELECT * FROM Prenotazione WHERE data_evento = ?";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, formattedDate);
			ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				String id=rs.getString("id_prenotazione");
	            LocalDateTime eventDate = rs.getTimestamp("data_evento").toLocalDateTime();
	            LocalDateTime time = rs.getTimestamp("ora_evento").toLocalDateTime();
	            Integer n = rs.getInt("n_persone");
	            String note=rs.getString("note");
	            String client_id=rs.getString("cliente_id");
	            LocalDateTime dateTime = eventDate.withHour(time.getHour())
                        .withMinute(time.getMinute())
                        .withSecond(time.getSecond());
	            Reservation reservation = new Reservation(id, dateTime, n, note, client_id);
	            dailyReservation.add(reservation);
			}
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
		return dailyReservation;
	}
	
	public ArrayList<Reservation> getCustomerReservation(Customer customer)throws ClassNotFoundException, SQLException{
		ArrayList<Reservation> customerReservation= new ArrayList<>();
		String query = "SELECT * FROM Prenotazione WHERE cliente_id = ?";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, customer.getId());
			ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				String id=rs.getString("id_prenotazione");
	            LocalDateTime eventDate = rs.getTimestamp("data_evento").toLocalDateTime();
	            LocalDateTime time = rs.getTimestamp("ora_evento").toLocalDateTime();
	            Integer n = rs.getInt("n_persone");
	            String note=rs.getString("note");
	            String client_id=rs.getString("cliente_id");
	            LocalDateTime dateTime = eventDate.withHour(time.getHour())
                        .withMinute(time.getMinute())
                        .withSecond(time.getSecond());
	            Reservation reservation = new Reservation(id, dateTime, n, note, client_id);
	            customerReservation.add(reservation);
			}
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
		return customerReservation;
	}
	
	public ArrayList<Reservation> getAllReservation()throws ClassNotFoundException, SQLException{
		ArrayList<Reservation> allReservation= new ArrayList<>();
		String query = "SELECT * FROM Prenotazione";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				String id=rs.getString("id_prenotazione");
	            LocalDateTime eventDate = rs.getTimestamp("data_evento").toLocalDateTime();
	            LocalDateTime time = rs.getTimestamp("ora_evento").toLocalDateTime();
	            Integer n = rs.getInt("n_persone");
	            String note=rs.getString("note");
	            String client_id=rs.getString("cliente_id");
	            LocalDateTime dateTime = eventDate.withHour(time.getHour())
                        .withMinute(time.getMinute())
                        .withSecond(time.getSecond());
	            Reservation reservation = new Reservation(id, dateTime, n, note, client_id);
	            allReservation.add(reservation);
			}
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
		return allReservation;
	}
	
	
	
}
