package Orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import domain_model.Order;
import domain_model.Reservation;
import domain_model.Table;

public class OrderDAO {

	public void insertOrder(Order order) throws ClassNotFoundException, SQLException {
		String query = "INSERT INTO Ordini (id_cliente, id_prenotazione, id_piatto, id_tavolo) VALUES (?,?,?,?)";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, order.getId_customer());
			statement.setString(2, order.getId_reservation());
			statement.setString(3, order.getId_food());
			statement.setString(4, order.getId_table());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteOrder(Order order)throws ClassNotFoundException, SQLException{
		String query = "DELETE FROM Ordini WHERE id_ordine = ?";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, order.getId());
			statement.executeUpdate();
			statement.close();
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
		}		
	}
	
	public ArrayList<Order> getAllOrder()throws ClassNotFoundException, SQLException{
		ArrayList<Order> allOrder= new ArrayList<>();
		String query = "SELECT * FROM Order";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				String id=rs.getString("id_ordine");
				String id_customer=rs.getString("id_cliente");
	            String id_reservation=rs.getString("id_prenotazione");
				String id_piatto = rs.getString("id_piatto");
				String id_tavolo=rs.getString("id_tavolo");
				boolean state=rs.getBoolean("stato");
				Order order=new Order(id, id_customer, id_reservation, id_piatto, id_tavolo, state);
				allOrder.add(order);
			}
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
		return allOrder;
	}
	
	
	public void modifyOrderState(Order order)throws ClassNotFoundException, SQLException{
		String query="UPDATE Ordini SET stato = true WHERE id_piatto=? AND id_order=?";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, order.getId_food());
			statement.setString(2, order.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Order> getTableOrder(Table t)throws ClassNotFoundException, SQLException{
		ArrayList<Order> tableOrder= new ArrayList<>();
		String query = "SELECT * FROM Order WHERE id_tavolo=?";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, t.getId());
			ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				String id=rs.getString("id_ordine");
				String id_customer=rs.getString("id_cliente");
	            String id_reservation=rs.getString("id_prenotazione");
				String id_piatto = rs.getString("id_piatto");
				String id_tavolo=rs.getString("id_tavolo");
				boolean state=rs.getBoolean("stato");
				Order order=new Order(id, id_customer, id_reservation, id_piatto, id_tavolo, state);
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
		String query = "SELECT * FROM Order WHERE stato=?";	
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setBoolean(1,false);
			ResultSet rs = statement.executeQuery();	
			while(rs.next()) {
				String id=rs.getString("id_ordine");
				String id_customer=rs.getString("id_cliente");
	            String id_reservation=rs.getString("id_prenotazione");
				String id_piatto = rs.getString("id_piatto");
				String id_tavolo=rs.getString("id_tavolo");
				boolean state=rs.getBoolean("stato");
				Order order=new Order(id, id_customer, id_reservation, id_piatto, id_tavolo, state);
				tableOrder.add(order);
			}
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
		return tableOrder;
	}
	
	public int calculateBill(Reservation reservation) throws ClassNotFoundException, SQLException {
		Integer bill = null;
		String query = "SELECT o.id_prenotazione, SUM(m.prezzo) AS costo_totale\r\n" + "FROM Ordini o\r\n"
				+ "JOIN Menù m ON o.id_piatto = m.id_piatto\r\n" + "WHERE o.id_prenotazione = ? \r\n"
				+ "GROUP BY o.id_prenotazione";
		try (Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, reservation.getId());
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				bill = rs.getInt("costo_totale");
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
		return bill;

	}
}
