package Orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import domain_model.Table;

public class TableDAO {

	public int getTotalSeat()throws SQLException, ClassNotFoundException {
	    String query = "SELECT SUM(n_posti) FROM Tavolo ";
	    try (Connection connection = DatabaseConnect.getConnection();
	            PreparedStatement statement = connection.prepareStatement(query)) {
	        ResultSet rs = statement.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1); // Ottieni il valore dalla prima colonna del risultato
	        } else {
	            return 0; // Nel caso non ci siano risultati
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        return 0; // o un valore di default in caso di errore
	    }
	}
	
	public ArrayList<Table> getAllTable() throws ClassNotFoundException, SQLException {
		ArrayList<Table> tableList = new ArrayList<>();
		String query = "SELECT * FROM Tavolo";
		try (Connection connection = DatabaseConnect.getConnection();
				Statement statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(query)) {
			while (rs.next()) {
				Integer id = rs.getInt("id_tavolo");
				Integer name = rs.getInt("numero");
				Integer seat = rs.getInt("n_posti");
				Table table= new Table(id,name,seat);
				tableList.add(table);
			}
			statement.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return tableList;
	}

	

	


}
