package Test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Orm.CustomerDAO;
import Orm.MenuDAO;
import Orm.OrderDAO;
import Orm.ReservationDAO;
import domain_model.Customer;
import domain_model.Food;
import domain_model.Order;
import domain_model.Reservation;

public class OrderDAOTest {

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
		int id = menuDAO.insertDish(food);
		food.setId(id);
		
		order=new Order(null,reservation.getId(),food.getId(), reservation.getTable(), false);
		int i=orderDAO.insertOrder(order);
		order.setId(i);
	}
	
	@Test
	public void insertOrderTest() throws ClassNotFoundException, SQLException {
		assertNotNull(orderDAO.getOrder(order.getId()));
		assertEquals(food.getId(), orderDAO.getOrder(order.getId()).getId_food());
		assertEquals(reservation.getId(),orderDAO.getOrder(order.getId()).getId_reservation());
	}
	
	@Test
	public void deleteOrderTest() throws ClassNotFoundException, SQLException {
		orderDAO.deleteOrder(order.getId());
		assertNull(orderDAO.getOrder(order.getId()));
	}
	
	@Test
	public void getAllOrderTest() throws ClassNotFoundException, SQLException {
		ArrayList<Order> orders=orderDAO.getAllOrder();
		
		assertNotNull(orders);
		assertTrue(orders.size()>=1);
		boolean orderFound = false;
		for(Order o: orders) {
			if(o.getId().equals(order.getId()))
				orderFound=true;
		}
		assertEquals(true,orderFound);
	}
	
	@Test
	public void modifyOrderStateTest() throws ClassNotFoundException, SQLException {
		orderDAO.modifyOrderState(order.getId());
		assertTrue(orderDAO.getOrder(order.getId()).isState());
	}
	
	@Test
	public void calculateBillTest() throws ClassNotFoundException, SQLException {
		assertEquals(10, orderDAO.calculateBill(order.getId_reservation()));
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
