package Orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain_model.Food;

public class MenuDAO {

	public Integer insertDish(Food food) throws ClassNotFoundException, SQLException {
	    String query = "INSERT INTO Menù(piatto, prezzo, descrizione) VALUES (?,?,?)";
	    try (Connection connection = DatabaseConnect.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	        statement.setString(1, food.getName());
	        statement.setDouble(2, food.getCost());
	        statement.setString(3, food.getDescription());
	        int rowsInserted = statement.executeUpdate();
	        if (rowsInserted > 0) {
	            ResultSet generatedKeys = statement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1);
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public boolean deleteDish(Integer id) throws ClassNotFoundException, SQLException {
	    String deleteOrdersQuery = "DELETE FROM Ordini WHERE id_piatto = ?";
	    String deleteDishQuery = "DELETE FROM Menù WHERE id_piatto = ?";
	    
	    try (Connection connection = DatabaseConnect.getConnection();
	         PreparedStatement deleteOrdersStatement = connection.prepareStatement(deleteOrdersQuery);
	         PreparedStatement deleteDishStatement = connection.prepareStatement(deleteDishQuery)) {
	        
	        // Elimina gli ordini associati al piatto
	        deleteOrdersStatement.setInt(1, id);
	        deleteOrdersStatement.executeUpdate();
	        
	        // Elimina il piatto dal menù
	        deleteDishStatement.setInt(1, id);
	        deleteDishStatement.executeUpdate();
	        
	        return true;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	
	public boolean checkItem(Integer id) throws ClassNotFoundException, SQLException {
		String query = "SELECT * FROM Menù WHERE id_piatto = ?";
		try (Connection connection = DatabaseConnect.getConnection();
		         PreparedStatement statement = connection.prepareStatement(query)) {
		        statement.setInt(1, id);
		        ResultSet rs = statement.executeQuery();
				return rs.next();
			}catch (SQLException | ClassNotFoundException e ) {
				e.printStackTrace();
				return false;
			}
	}
	
	public ArrayList<Food> getAllDish() throws ClassNotFoundException, SQLException {
		ArrayList<Food> menu = new ArrayList<>();
		String query = "SELECT * FROM Menù";
		try (Connection connection = DatabaseConnect.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(query)) {
			while (rs.next()) {
				Integer id = rs.getInt("id_piatto");
				String name = rs.getString("piatto");
				Double cost = rs.getDouble("prezzo");
				String description = rs.getString("descrizione");
				Food food = new Food(id, name, cost, description);
				menu.add(food);
			}
			statement.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return menu;
	}
	
	public String getNameDish(Integer id) throws ClassNotFoundException, SQLException {
		String query = "SELECT piatto FROM Menù WHERE id_piatto = ?";
		try (Connection connection = DatabaseConnect.getConnection();
		         PreparedStatement statement = connection.prepareStatement(query)) {
		        statement.setInt(1, id);
		        ResultSet rs = statement.executeQuery();
				
				if (rs.next()) {
					return rs.getString("piatto");
					} else {
	                   return null;
	            }
			}catch (SQLException | ClassNotFoundException e ) {
				e.printStackTrace();
				return null;
			}
	}
	
	public Food getDish(Integer id) throws ClassNotFoundException, SQLException {
	    String query = "SELECT * FROM Menù WHERE id_piatto = ?";
	    try (Connection connection = DatabaseConnect.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setInt(1, id);
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                return new Food(id, rs.getString("piatto"), rs.getDouble("prezzo"), rs.getString("descrizione"));
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return null;
	}


}
