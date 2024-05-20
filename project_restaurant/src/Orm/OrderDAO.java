package Orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain_model.Order;
import domain_model.Reservation;
import domain_model.Table;

public class OrderDAO {

	public Integer insertOrder(Order order) throws ClassNotFoundException, SQLException {
		String query = "INSERT INTO Ordini ( id_prenotazione, id_piatto, id_tavolo) VALUES (?,?,?)";
		try (Connection connection = DatabaseConnect.getConnection();
		         PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))  {
			statement.setInt(1, order.getId_reservation());
			statement.setInt(2, order.getId_food());
			statement.setInt(3, order.getId_table());
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
	
	public void deleteOrder(Integer id)throws ClassNotFoundException, SQLException{
		String query = "DELETE FROM Ordini WHERE id_ordine = ?";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.executeUpdate();
			statement.close();
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
		}		
	}
	
	public ArrayList<Order> getAllOrder()throws ClassNotFoundException, SQLException{
		ArrayList<Order> allOrder= new ArrayList<>();
		String query = "SELECT * FROM Ordini";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				Integer id=rs.getInt("id_ordine");
				Integer id_reservation=rs.getInt("id_prenotazione");
				Integer id_piatto = rs.getInt("id_piatto");
				Integer id_tavolo=rs.getInt("id_tavolo");
				boolean state=rs.getBoolean("stato");
				Order order=new Order(id, id_reservation, id_piatto, id_tavolo, state);
				allOrder.add(order);
			}
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
		return allOrder;
	}
	
	public Order getOrder(Integer id)throws ClassNotFoundException, SQLException{
		String query = "SELECT * FROM Ordini WHERE id_ordine=?";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();	
			 if (rs.next()) {
		            Integer i = rs.getInt("id_ordine");
		            Integer id_reservation = rs.getInt("id_prenotazione");
		            Integer id_piatto = rs.getInt("id_piatto");
		            Integer id_tavolo = rs.getInt("id_tavolo");
		            boolean state = rs.getBoolean("stato");
		            Order order = new Order(i, id_reservation, id_piatto, id_tavolo, state);
		            return order;
		        } else {
		            // Nessun ordine trovato con l'ID specificato
		            return null;
		        }
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public void modifyOrderState(Integer id)throws ClassNotFoundException, SQLException{
		String query="UPDATE Ordini SET stato = true WHERE id_ordine=?";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1,id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public ArrayList<Order> getReservationOrder(Integer id)throws ClassNotFoundException, SQLException{
		ArrayList<Order> orderL= new ArrayList<>();
		String query = "SELECT * FROM Ordini WHERE id_prenotazione=?";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				Integer i=rs.getInt("id_ordine");
				Integer id_reservation=rs.getInt("id_prenotazione");
				Integer id_piatto = rs.getInt("id_piatto");
				Integer id_tavolo=rs.getInt("id_tavolo");
				boolean state=rs.getBoolean("stato");
				Order order=new Order(i, id_reservation, id_piatto, id_tavolo, state);
				orderL.add(order);
			}
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
		return orderL;
	}
	
	
	
	public ArrayList<Order> getTableOrder(Table t)throws ClassNotFoundException, SQLException{
		ArrayList<Order> tableOrder= new ArrayList<>();
		String query = "SELECT * FROM Ordini WHERE id_tavolo=?";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, t.getId());
			ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				Integer id=rs.getInt("id_ordine");
				Integer id_reservation=rs.getInt("id_prenotazione");
				Integer id_piatto = rs.getInt("id_piatto");
				Integer id_tavolo=rs.getInt("id_tavolo");
				boolean state=rs.getBoolean("stato");
				Order order=new Order(id, id_reservation, id_piatto, id_tavolo, state);
				tableOrder.add(order);
			}
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
		return tableOrder;
	}
	

	public ArrayList<Order> getUncompletedOrder()throws ClassNotFoundException, SQLException{
		ArrayList<Order> tableOrder= new ArrayList<>();
		String query = "SELECT * FROM Ordini WHERE stato=? OR stato IS NULL";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setBoolean(1,false);
			ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				Integer id=rs.getInt("id_ordine");
				Integer id_reservation=rs.getInt("id_prenotazione");
				Integer id_piatto = rs.getInt("id_piatto");
				Integer id_tavolo=rs.getInt("id_tavolo");
				boolean state=rs.getBoolean("stato");
				Order order=new Order(id, id_reservation, id_piatto, id_tavolo, state);
				tableOrder.add(order);
			}
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
		return tableOrder;
	}
	
	public int calculateBill(Integer id) throws ClassNotFoundException, SQLException {
		Integer bill = null;
		String query = "SELECT o.id_prenotazione, SUM(m.prezzo) AS costo_totale\r\n" + "FROM Ordini o\r\n"
				+ "JOIN Menù m ON o.id_piatto = m.id_piatto\r\n" + "WHERE o.id_prenotazione = ? \r\n"
				+ "GROUP BY o.id_prenotazione";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				bill = rs.getInt("costo_totale");
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
		if(bill==null)
			return 0;
		return bill;

	}
}
