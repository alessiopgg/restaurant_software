package business_logic;

import java.sql.SQLException;

import Orm.CustomerDAO;
import Orm.MenuDAO;
import Orm.ReservationDAO;
import domain_model.Customer;
import domain_model.Reservation;

public class CustomerController {

	public void signIn(Customer customer)throws ClassNotFoundException, SQLException {
		CustomerDAO customerDAO= new CustomerDAO();
		if(customerDAO.checkNumberPhone(customer)==false) {
			customerDAO.addCustomer(customer);
		}else {
			System.out.println("Phone number alredy used...");
		}
	}
	
	public boolean login(Customer customer)throws ClassNotFoundException, SQLException {
		CustomerDAO customerDAO= new CustomerDAO();
		if(customerDAO.findClient(customer)==true) {
			return true;
		}else return false;
	}
	
	public void deleteAccount(Customer customer)throws ClassNotFoundException, SQLException {
		CustomerDAO customerDAO= new CustomerDAO();
		if(customerDAO.findClient(customer)==true) {
			customerDAO.deleteCustomer(customer.getId());
		}else System.out.println("Account not found...");
	}
	
	public void viewMenu()throws ClassNotFoundException, SQLException {
		MenuDAO menuDAO=new MenuDAO();
		System.out.println(menuDAO.getAllDish());

	}
	
	public void viewMyReservation(Customer customer)throws ClassNotFoundException, SQLException {
		ReservationDAO reservationDAO=new ReservationDAO();
		reservationDAO.getCustomerReservation(customer);
	}
	
	public void newReservation(Reservation reservation)throws ClassNotFoundException, SQLException{
		ReservationDAO reservationDAO=new ReservationDAO();
		if(reservationDAO.checkReservation(reservation)==true) {
			reservationDAO.insertReservation(reservation);
		}
		else System.out.println("Booking not available for these dates...");

	}
	
	public void deleteReservation(Reservation reservation)throws ClassNotFoundException, SQLException{
		ReservationDAO reservationDAO= new ReservationDAO();
		reservationDAO.deleteReservation(reservation);
	}
	
	
}
