package Orm;

import java.sql.*;
import java.util.ArrayList;

import domain_model.Customer;

public class CustomerDAO {

	public void addCustomer(Customer customer) throws ClassNotFoundException, SQLException {
		try {
			Connection connection = DatabaseConnect.getConnection();
			String query = "INSERT INTO Cliente (cognome, nome, telefono) VALUES (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, customer.getSurname());
			statement.setString(2, customer.getName());
			statement.setString(3, customer.getPhone());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}
	
	public void deleteCustomer(Integer id) throws ClassNotFoundException, SQLException  {
	    String deleteOrdersQuery = "DELETE FROM Ordini WHERE id_prenotazione IN (SELECT id_prenotazione FROM Prenotazione WHERE cliente_id = ?)";
        String deleteReservationQuery = "DELETE FROM Prenotazione WHERE cliente_id = ?";
		String deleteClient = "DELETE FROM Cliente WHERE cliente_id = ?";
		try (Connection connection = DatabaseConnect.getConnection();
				 PreparedStatement deleteOrdersStatement = connection.prepareStatement(deleteOrdersQuery);
		         PreparedStatement deleteReservationStatement = connection.prepareStatement(deleteReservationQuery);
		         PreparedStatement deleteClientStatement = connection.prepareStatement(deleteClient)) {
			
			deleteOrdersStatement.setInt(1, id);
	        deleteOrdersStatement.executeUpdate();
			
			deleteReservationStatement.setInt(1, id);
			deleteReservationStatement.executeUpdate();
			
			deleteClientStatement.setInt(1, id);
			deleteClientStatement.executeUpdate();
		} catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}
		
	public boolean findClient(Customer customer) throws ClassNotFoundException, SQLException  {
		String query = "SELECT * FROM Cliente WHERE cognome = ? AND telefono = ?";
		try(Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);){
			statement.setString(1, customer.getSurname());
			statement.setString(2, customer.getPhone());
			ResultSet rs= statement.executeQuery();
			return rs.next();
			
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<Customer> getAllCustomer()throws ClassNotFoundException, SQLException{
		ArrayList<Customer> customerList= new ArrayList<>();
		String query = "SELECT * FROM Cliente";
		try(Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);){
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Integer id=rs.getInt("cliente_id");
				String surname=rs.getString("cognome");
				String name=rs.getString("nome");
				String phone=rs.getString("telefono");
				Customer customer=new Customer(id, surname, name, phone);
				customerList.add(customer);
			}
		} catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return null;
		}
		return customerList;

	}
	
	public boolean checkNumberPhone(Customer customer)throws ClassNotFoundException, SQLException{
		String query = "SELECT * FROM Cliente WHERE telefono = ?";
		try(Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);){
			statement.setString(1, customer.getPhone());
			ResultSet rs = statement.executeQuery();
			// Se rs.next() restituisce true, significa che il numero di telefono è stato trovato nel database
	        // Altrimenti, il numero di telefono non è stato trovato nel database
	        return rs.next();
		}catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public Integer getId(String phone) throws ClassNotFoundException, SQLException {
	    String query = "SELECT cliente_id FROM Cliente WHERE telefono = ?";
	    try (Connection connection = DatabaseConnect.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, phone);
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
	
	public Customer getCustomer(Integer id) throws ClassNotFoundException, SQLException {
	    Customer customer = null;
	    String query = "SELECT * FROM Cliente WHERE cliente_id = ?";
	    try (Connection connection = DatabaseConnect.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setInt(1, id);
	        ResultSet rs = statement.executeQuery();
	        if (rs.next()) {
	            customer = new Customer(id, rs.getString("cognome"), rs.getString("nome"), rs.getString("telefono"));
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return customer;
	}


}


