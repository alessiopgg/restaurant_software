package Test.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Orm.TableDAO;
import domain_model.Table;

public class TableDAOTest {

	private TableDAO tableDAO;
	
	@Before
	public void setUp() {
		tableDAO = new TableDAO();
	}

    @Test
    public void testGetTotalSeat() throws ClassNotFoundException, SQLException {
        int totalSeat = tableDAO.getTotalSeat();
        assertTrue(totalSeat > 0);
    }

    @Test
    public void testGetAllTable() throws ClassNotFoundException, SQLException {
        // Verifica che l'elenco dei tavoli restituito non sia nullo
        ArrayList<Table> tableList = tableDAO.getAllTable();
        assertNotNull(tableList);     
        assertTrue(tableList.size() > 0);
        for (Table table : tableList) {
            assertNotNull(table.getId());
            assertNotNull(table.getNumber());
            assertNotNull(table.getSeatNumbers());
            assertTrue(table.getId() > 0);
            assertTrue(table.getNumber() > 0);
            assertTrue(table.getSeatNumbers() > 0);
        }
    }
}
	
	
