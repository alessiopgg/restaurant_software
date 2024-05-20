package Test.controllers;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Orm.CustomerDAO;
import Orm.MenuDAO;
import Orm.OrderDAO;
import Orm.ReservationDAO;
import business_logic.AdminController;
import business_logic.BrigadeController;
import domain_model.Customer;
import domain_model.Food;
import domain_model.Order;
import domain_model.Reservation;

public class BrigageControllerTest {
	private BrigadeController brigadeController;
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
	brigadeController= new BrigadeController();
	
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
public void markOrderTest() throws ClassNotFoundException, SQLException {
    AdminController adminController = new AdminController();
    assertEquals(false, orderDAO.getOrder(order.getId()).isState());
    brigadeController.markOrder(orderDAO.getOrder(order.getId()).getId(),adminController);
    assertEquals(true, orderDAO.getOrder(order.getId()).isState());

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