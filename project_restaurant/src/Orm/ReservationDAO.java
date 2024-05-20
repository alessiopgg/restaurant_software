package Orm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import domain_model.Customer;
import domain_model.Reservation;
import domain_model.Table;

public class ReservationDAO {

	public Integer insertReservation(Reservation r) throws ClassNotFoundException, SQLException {
		String query = "INSERT INTO Prenotazione(data_evento, ora_evento, n_persone, note, tavolo ,cliente_id) VALUES (?,?,?,?,?,?)";
		try (Connection connection = DatabaseConnect.getConnection();
		         PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			
			 // Inserisci la data
	        statement.setDate(1, Date.valueOf(r.getDate().toLocalDate())); // Converti LocalDateTime in LocalDate

	        // Inserisci l'ora
	        statement.setTime(2, Time.valueOf(r.getDate().toLocalTime())); // Converti LocalDateTime in LocalTime
			
			statement.setInt(3, r.getNumberOfPerson());
			statement.setString(4, r.getSpecialRequest());
			statement.setInt(5, r.getTable());
			statement.setInt(6, r.getName());
			int rowsInserted = statement.executeUpdate();
			 if (rowsInserted > 0) {
		            ResultSet generatedKeys = statement.getGeneratedKeys();
		            if (generatedKeys.next()) {
		                return generatedKeys.getInt(1);
		            }
		        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteReservation(Integer id)throws ClassNotFoundException, SQLException{
	    String deleteOrdersQuery = "DELETE FROM Ordini WHERE id_prenotazione = ?";
		String query = "DELETE FROM Prenotazione WHERE id_prenotazione = ?";
		try (Connection connection = DatabaseConnect.getConnection();
				 PreparedStatement deleteOrdersStatement = connection.prepareStatement(deleteOrdersQuery);
				PreparedStatement statement = connection.prepareStatement(query)) {
			
			deleteOrdersStatement.setInt(1, id);
	        deleteOrdersStatement.executeUpdate();
			
			statement.setInt(1, id);
			statement.executeUpdate();
			statement.close();
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}
	
	public Integer getIdCustomer(Integer id)throws ClassNotFoundException, SQLException{
		String query ="SELECT cliente_id FROM Prenotazione WHERE id_prenotazione = ?";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			 try (ResultSet rs = statement.executeQuery()) {
		            if (rs.next()) {
		                return rs.getInt("cliente_id");
		            } else {
		                return null;
		            }
		        }
		    } catch (SQLException | ClassNotFoundException e) {
		        e.printStackTrace();
		        return null;
			
		}
	
	}
	

	public Integer getIdTable(Integer idR)throws ClassNotFoundException, SQLException{
		String query ="SELECT tavolo FROM Prenotazione WHERE  id_prenotazione =?";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idR);
			 try (ResultSet rs = statement.executeQuery()) {
		            if (rs.next()) {
		                return rs.getInt("tavolo");
		            } else {
		                return null;
		            }
		        }
		    } catch (SQLException | ClassNotFoundException e) {
		        e.printStackTrace();
		        return null;
			
		}
	
	}
	
	public ArrayList<Reservation> getDailyReservation(LocalDateTime day)throws ClassNotFoundException, SQLException{
		ArrayList<Reservation> dailyReservation= new ArrayList<>();
		String query = "SELECT * FROM Prenotazione WHERE data_evento = ?";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDate(1, Date.valueOf(day.toLocalDate()));
			ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				Integer id=rs.getInt("id_prenotazione");
	            LocalDateTime eventDate = rs.getTimestamp("data_evento").toLocalDateTime();
	            LocalDateTime time = rs.getTimestamp("ora_evento").toLocalDateTime();
	            Integer n = rs.getInt("n_persone");
	            String note=rs.getString("note");
	            Integer t=rs.getInt("tavolo");
	            Integer client_id=rs.getInt("cliente_id");
	            LocalDateTime dateTime = eventDate.withHour(time.getHour())
                        .withMinute(time.getMinute())
                        .withSecond(time.getSecond());
	            Reservation reservation = new Reservation(id, dateTime, n, note, t,client_id);
	            dailyReservation.add(reservation);
			}
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
		return dailyReservation;
	}
	
	public ArrayList<Reservation> getCustomerReservation(Integer id)throws ClassNotFoundException, SQLException{
		ArrayList<Reservation> customerReservation= new ArrayList<>();
		String query = "SELECT * FROM Prenotazione WHERE cliente_id = ?";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				Integer idP=rs.getInt("id_prenotazione");
	            LocalDateTime eventDate = rs.getTimestamp("data_evento").toLocalDateTime();
	            LocalDateTime time = rs.getTimestamp("ora_evento").toLocalDateTime();
	            Integer n = rs.getInt("n_persone");
	            String note=rs.getString("note");
	            Integer t=rs.getInt("tavolo");
	            Integer client_id=rs.getInt("cliente_id");
	            LocalDateTime dateTime = eventDate.withHour(time.getHour())
                        .withMinute(time.getMinute())
                        .withSecond(time.getSecond());
	            Reservation reservation = new Reservation(idP, dateTime, n, note,t ,client_id);
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
				Integer id=rs.getInt("id_prenotazione");
	            LocalDateTime eventDate = rs.getTimestamp("data_evento").toLocalDateTime();
	            LocalDateTime time = rs.getTimestamp("ora_evento").toLocalDateTime();
	            Integer n = rs.getInt("n_persone");
	            String note=rs.getString("note");
	            Integer t=rs.getInt("tavolo");
	            Integer client_id=rs.getInt("cliente_id");
	            LocalDateTime dateTime = eventDate.withHour(time.getHour())
                        .withMinute(time.getMinute())
                        .withSecond(time.getSecond());
	            Reservation reservation = new Reservation(id, dateTime, n, note,t, client_id);
	            allReservation.add(reservation);
			}
			return allReservation;

		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void setBill(Integer bill,Integer id)throws ClassNotFoundException, SQLException{
		String query="UPDATE Prenotazione SET conto = ? WHERE id_prenotazione = ?";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, bill);
			statement.setInt(2, id);
			statement.executeUpdate();
			statement.close();
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();

		}
	}
	
	
	public ArrayList<Table> getTableDate(LocalDateTime dateTime)throws ClassNotFoundException, SQLException{//ritorna i tavoli occupati in un giorno ad una certa ora
		ArrayList<Table> tableList= new ArrayList<>();
		String query = "SELECT * FROM Prenotazione WHERE data_evento = ? AND ora_evento BETWEEN ? AND ?";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDate(1, Date.valueOf(dateTime.toLocalDate()));
			  Time startTime = Time.valueOf(dateTime.minusHours(1).minusMinutes(30).toLocalTime());
		        // Calcola l'ora un'ora e mezza dopo la data e ora specificate
		        Time endTime = Time.valueOf(dateTime.plusHours(1).plusMinutes(30).toLocalTime());
		        // Imposta l'ora della prenotazione - un'ora e mezza prima
		        statement.setTime(2, startTime);
		        // Imposta l'ora della prenotazione + un'ora e mezza
		        statement.setTime(3, endTime);	
		      ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				Integer id_table=rs.getInt("tavolo");
				Integer n= rs.getInt("n_persone");
				Table table = new Table(id_table,null,n);
	            tableList.add(table);
			}
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
		return tableList;
	}
	
	
	
	
	
}
