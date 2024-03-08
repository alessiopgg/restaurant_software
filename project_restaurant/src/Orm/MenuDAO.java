package Orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain_model.Customer;
import domain_model.Food;

public class MenuDAO {

	public void insertDish(Food food) throws ClassNotFoundException, SQLException {
		String query = "INSERT INTO Menù(piatto, prezzo, descrizione) VALUES (?,?,?)";
		try(Connection connection=DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);){
			statement.setString(1,food.getName());
			statement.setDouble(2,food.getCost());
			statement.setString(3,food.getDescription());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException | ClassNotFoundException e ) {
			e.printStackTrace();
		}
	}
	
	public void deleteDish(Food food)throws ClassNotFoundException, SQLException {
		String query = "DELETE FROM Menù WHERE piatto = ? AND prezzo = ? AND descrizione = ?";
		try(Connection connection = DatabaseConnect.getConnection();
				PreparedStatement statement = connection.prepareStatement(query)){
			statement.setString(1, food.getName());
			statement.setDouble(2, food.getCost());
			statement.setString(3, food.getDescription());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
	        e.printStackTrace();
	    }	
	}
	
	
	public ArrayList getAllDish() throws ClassNotFoundException, SQLException {
		ArrayList<Food> menu = new ArrayList<>();
		String query = "SELECT * FROM Menù";
		try (Connection connection = DatabaseConnect.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(query)) {
			while (rs.next()) {
				String id = rs.getString("id_piatto");
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

}
