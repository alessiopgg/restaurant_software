package Test.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Orm.CustomerDAO;
import Orm.MenuDAO;
import Orm.OrderDAO;
import Orm.ReservationDAO;
import business_logic.CustomerController;
import domain_model.Customer;
import domain_model.Food;
import domain_model.Order;
import domain_model.Reservation;

public class CustomerControllerTest {
	private CustomerController customerController;
	
	private OrderDAO orderDAO;
	private Order order;
	
	private ReservationDAO reservationDAO;
	private Reservation reservation;
	
	private MenuDAO menuDAO;
	private Food food;
	
	private CustomerDAO customerDAO;
	private Customer customer;


	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		customerController = new CustomerController();
		
		orderDAO=new OrderDAO();
		reservationDAO= new ReservationDAO();
		menuDAO = new MenuDAO();
		customerDAO = new CustomerDAO();
		
		customer = new Customer(null,"surname", "name", "1234567899");
		customerController.signIn(customer);

		customer.setId(customerDAO.getId(customer.getPhone()));		
        
        reservation=new Reservation(null, LocalDateTime.of(2002, 1, 1, 20, 0) , 5, "request", 10,customer.getId());
		int rId = reservationDAO.insertReservation(reservation);
		reservation.setId(rId);
		reservation.setName(reservationDAO.getIdCustomer(reservation.getId()));
		
		food = new Food(null, "name", 10.00, "description");
		int id = menuDAO.insertDish(food);
		food.setId(id);
		
		order=new Order(null,reservation.getId(),food.getId(), reservation.getTable(), false);
		int i=orderDAO.insertOrder(order);
		order.setId(i);
	}
	
	@Test
    public void signInTest() throws ClassNotFoundException, SQLException {
        assertNotNull(customer);
        assertEquals(customer.getId(),customerController.getId("1234567899")); 
      
        Customer customer1=new Customer(null, null, null, "1234567899");
        assertFalse(customerController.signIn(customer1));
    }
	
	 @Test
	    public void loginTest() throws ClassNotFoundException, SQLException {
	        boolean loginSuccess = customerController.login(customer);
	        assertTrue(loginSuccess);

	        Customer noExistingCustomer = new Customer(null, "nonExistent", "user", "0000000000");
	        boolean loginFailure = customerController.login(noExistingCustomer);
	        assertFalse(loginFailure);
	    }
	 
	 @Test
	    public void deleteAccountTest() throws ClassNotFoundException, SQLException {
	        assertTrue(customerDAO.findClient(customer));
		 customerController.deleteAccount(customer);
	        assertFalse(customerDAO.findClient(customer));
	 }

	 @Test
	    public void checkMyReservationTest() throws ClassNotFoundException, SQLException {
	        boolean reservationExists = customerController.checkMyReservation(reservation.getId(), customerDAO.getCustomer(customer.getId()));
	        assertTrue(reservationExists);

	    }
	 
	 @Test
	    public void deleteReservationTest() throws ClassNotFoundException, SQLException {
	        customerController.deleteReservation(reservation.getId());
	        assertFalse(customerController.checkMyReservation(reservation.getId(), customer));
	 }
	
	
	@After
	 public void tearDown() {
       try {
           orderDAO.deleteOrder(order.getId());
           menuDAO.deleteDish(food.getId());
           reservationDAO.deleteReservation(reservation.getId());
           customerDAO.deleteCustomer(customer.getId());
       } catch (SQLException | ClassNotFoundException e) {
           e.printStackTrace();
       }
   }	
}
