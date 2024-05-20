package Test.controllers;

import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Orm.CustomerDAO;
import Orm.MenuDAO;
import Orm.OrderDAO;
import Orm.ReservationDAO;
import business_logic.AdminController;
import domain_model.Order;
import domain_model.Reservation;
import domain_model.Customer;
import domain_model.Food;


public class AdminControllerTest {
	private AdminController adminController;
	private OrderDAO orderDAO;
	private Order order;
	private ReservationDAO reservationDAO;
	private Reservation reservation;
	private MenuDAO menuDAO;
	private Food food;
	private CustomerDAO customerDAO;
	private Customer customer;
	    
	    @Before
	    public void setUp() throws Exception {
	        adminController = new AdminController();
	      
	        orderDAO=new OrderDAO();
			reservationDAO= new ReservationDAO();
			menuDAO = new MenuDAO();
			customerDAO = new CustomerDAO();
			
			customer = new Customer(null,"surname", "name", "1234567899");
	        customerDAO.addCustomer(customer);
	        customer.setId(customerDAO.getId(customer.getPhone()));		
	        
	        reservation=new Reservation(null, LocalDateTime.of(2002, 1, 1, 20, 0) , 5, "request", 10,customer.getId());
			int rId = reservationDAO.insertReservation(reservation);
			reservation.setId(rId);
			reservation.setName(reservationDAO.getIdCustomer(reservation.getId()));
			
			food = new Food(null, "name", 10.00, "description");
	        adminController.addToMenu(food);
	        
			order=new Order(null,reservation.getId(),food.getId(), reservation.getTable(), false);
	        adminController.createOrder(order);
	    }

	    @Test
	    public void createOrderTest() throws Exception {
	        assertNotNull(order.getId());
	    }

	    @Test
	    public void deleteOrderTest() throws Exception {
	        assertNotNull(order.getId());
	        adminController.deleteOrder(order.getId());
	        assertNull(orderDAO.getOrder(order.getId()));
	    }

	    @Test
	    public void addToMenuTest() throws Exception {
	        assertNotNull(food.getId());
	        assertTrue(menuDAO.checkItem(food.getId()));

	    }

	    @Test
	    public void deleteToMenuTest() throws Exception {
	        assertNotNull(food.getId());
	        adminController.deleteToMenu(food.getId());
	        assertNull(menuDAO.getDish(food.getId()));
	        assertFalse(menuDAO.checkItem(food.getId()));
	    }

	   
	    @Test
	    public void billTest() throws Exception {
	        int bill = adminController.bill(order.getId_reservation());
	        assertEquals(10, bill); // Assumendo un totale di esempio
			Order order1=new Order(null,reservation.getId(),food.getId(), reservation.getTable(), false);
			adminController.createOrder(order1);
	        bill = adminController.bill(order1.getId_reservation());
			assertEquals(20, bill);
			adminController.deleteOrder(order1.getId());
			assertEquals(10, adminController.bill(order1.getId_reservation()));
	    }

	   
	    @Test
	    public void checkDishTest() throws Exception {
	        boolean exists = adminController.checkDish(food.getId());
	        assertTrue(exists);
	    }

	    @Test
	    public void getIdCustomerTest() throws Exception {
	        Integer customerId = adminController.getIdCustomer(reservation.getId());
	        assertNotNull(customerId);
	    }

	    @Test
	    public void getIdTableTest() throws Exception {
	        Integer tableId = adminController.getIdTable(reservation.getId());
	        assertNotNull(tableId);
	    }
	    
	    @After
	    public void tearDown() throws Exception {
	    	
	    	orderDAO.deleteOrder(order.getId());
            menuDAO.deleteDish(food.getId());
            reservationDAO.deleteReservation(reservation.getId());
            customerDAO.deleteCustomer(customer.getId());
	    }

}

