package business_logic;

import java.sql.SQLException;
import java.time.LocalDateTime;

import Orm.MenuDAO;
import Orm.OrderDAO;
import Orm.ReservationDAO;
import domain_model.Customer;
import domain_model.Food;
import domain_model.Order;
import domain_model.Reservation;

public class AdminController {

	public void createOrder(Order order)throws ClassNotFoundException, SQLException {
		OrderDAO orderDAO=new OrderDAO();
		orderDAO.insertOrder(order);
		
	}
	
	public void deleteOrder(Order order)throws ClassNotFoundException, SQLException {
		OrderDAO orderDAO=new OrderDAO();
		orderDAO.deleteOrder(order);
	}
	
	public void addToMenu(Food food)throws ClassNotFoundException, SQLException {
		MenuDAO menuDAO=new MenuDAO();
		menuDAO.insertDish(food);
	}
	
	public void deleteToMenu(Food food)throws ClassNotFoundException, SQLException {
		MenuDAO menuDAO=new MenuDAO();
		menuDAO.deleteDish(food);
	}
	
	public void viewMenu()throws ClassNotFoundException, SQLException {
		MenuDAO menuDAO=new MenuDAO();
		System.out.println(menuDAO.getAllDish());

	}
	
	public void viewAllReservation()throws ClassNotFoundException, SQLException {
		ReservationDAO reservationDAO=new ReservationDAO();
		reservationDAO.getAllReservation();
	}
	
	public void viewDailyReservation(LocalDateTime dateTime)throws ClassNotFoundException, SQLException {
		ReservationDAO reservationDAO=new ReservationDAO();
		reservationDAO.getDailyReservation(dateTime);
	}
	
	public void viewCustomerReservation(Customer customer)throws ClassNotFoundException, SQLException {
		ReservationDAO reservationDAO=new ReservationDAO();
		reservationDAO.getCustomerReservation(customer);
	}
	
	public int bill(Reservation reservation)throws ClassNotFoundException, SQLException {
		OrderDAO orderDAO = new OrderDAO();
		reservation.setBill(orderDAO.calculateBill(reservation));
		return reservation.getBill();
	}
	
	public void update(Order order) {
		System.out.println(order.getId()+" is ready...");
	}
	
	
}
