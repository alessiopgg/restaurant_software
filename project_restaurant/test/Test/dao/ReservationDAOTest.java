package Test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
import Orm.ReservationDAO;
import domain_model.Customer;
import domain_model.Order;
import domain_model.Reservation;

public class ReservationDAOTest {
	
	private ReservationDAO reservationDAO;
	private Reservation reservation;
	
	private CustomerDAO customerDAO;
	private Customer customer;
	
	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		reservationDAO= new ReservationDAO();
		customerDAO = new CustomerDAO();
		
		customer = new Customer(null,"surname", "name", "1234567899");
        customerDAO.addCustomer(customer);
        customer.setId(customerDAO.getId(customer.getPhone()));		
        
        reservation=new Reservation(null, LocalDateTime.of(2002, 1, 1, 20, 0) , 5, "request", 10,customer.getId());
		int rId = reservationDAO.insertReservation(reservation);
		reservation.setId(rId);
	    reservationDAO.setBill(5, reservation.getId());
	}

	@Test
	public void insertReservationTest() throws ClassNotFoundException, SQLException {
		assertNotNull(reservationDAO.getIdCustomer(reservation.getId()));
	}
	
	@Test
	public void deleteReservationTest()throws ClassNotFoundException, SQLException {
		reservationDAO.deleteReservation(reservation.getId());
		assertNull(reservationDAO.getIdCustomer(reservation.getId()));

	}
	@Test
	public void getAllReservationTest()throws ClassNotFoundException, SQLException {
		ArrayList<Reservation> reservationList=reservationDAO.getAllReservation();
		
		assertNotNull(reservationList);
		assertTrue(reservationList.size()>=1);
		boolean reservationListFound = false;
		for (Reservation r : reservationList) {
		    if (r.getId().equals(reservation.getId())) // Confronta gli ID delle prenotazioni
		        reservationListFound = true;
		}
		assertEquals(true, reservationListFound);
	}
	
	@Test
	public void setBillTest() throws ClassNotFoundException, SQLException {
	    reservationDAO.setBill(5, reservation.getId());
	    for(Reservation r: reservationDAO.getAllReservation()) {
	    	if(r.getId()==reservation.getId())
	    	    assertEquals(5, r.getBill().intValue());

	    }
	}

	
	
	@After
	 public void tearDown() {
        try {
            reservationDAO.deleteReservation(reservation.getId());
            customerDAO.deleteCustomer(customer.getId());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
}
