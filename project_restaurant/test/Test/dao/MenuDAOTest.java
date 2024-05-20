package Test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Orm.MenuDAO;
import domain_model.Food;

public class MenuDAOTest {

	private MenuDAO menuDAO;
	private Food food;

	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		menuDAO = new MenuDAO();
		food = new Food(null, "name", 10.00, "description");
		int id = menuDAO.insertDish(food);
		food.setId(id);

	}
	
	@Test
	public void insertDishTest()throws ClassNotFoundException, SQLException{
		assertTrue(menuDAO.checkItem(food.getId()));
		assertEquals("name", menuDAO.getDish(food.getId()).getName());
		assertEquals(10.00, menuDAO.getDish(food.getId()).getCost(), 0.001);
		assertEquals("description", menuDAO.getDish(food.getId()).getDescription());
		
	}
	
	@Test
	public void deleteDishTest()throws ClassNotFoundException, SQLException {
		menuDAO.deleteDish(food.getId());
		assertNull(menuDAO.getDish(food.getId()));
		assertFalse(menuDAO.checkItem(food.getId()));
	}
    //TODO: FIX modifica il db trova soluzione

	@Test
	public void getAllDishTest() throws ClassNotFoundException, SQLException {
	    // Ottieni tutti i piatti dal menu
	    ArrayList<Food> menuItems = menuDAO.getAllDish();
	    // Verifica che l'elenco restituito non sia nullo
	    assertNotNull(menuItems);
	    // Verifica che l'elenco contenga i piatti inseriti
	    assertTrue(menuItems.size() >= 2); // Almeno due piatti devono essere presenti
	    boolean menuItemsFound =false;
	    Food food1=new Food(null, null, null, null);
	    boolean menuItemsFound1 = false;
	    for (Food f : menuItems) {
	        if (f.getId().equals(food.getId())) {
	        	menuItemsFound = true;
	        }
	        if (f.getId().equals(food1.getId())) {
	        	menuItemsFound1 = true;
	        }
	        
	    }
	    assertEquals(true,menuItemsFound);
	    assertEquals(false,menuItemsFound1);
	   
	    
	}
	
	 @After
	    public void tearDown() {
	        try {
	        	menuDAO.deleteDish(food.getId());
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }
}
