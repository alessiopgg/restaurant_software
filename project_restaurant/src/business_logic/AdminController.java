package business_logic;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Orm.CustomerDAO;
import Orm.MenuDAO;
import Orm.OrderDAO;
import Orm.ReservationDAO;
import domain_model.Customer;
import domain_model.Food;
import domain_model.Order;
import domain_model.Reservation;

public class AdminController implements OrderObserver{

	public void createOrder(Order order)throws ClassNotFoundException, SQLException {
		OrderDAO orderDAO=new OrderDAO();
		order.setState(false);
		orderDAO.insertOrder(order);
		order.attach(this);
		
	}
	
	public void deleteOrder(Integer id)throws ClassNotFoundException, SQLException {
		OrderDAO orderDAO=new OrderDAO();
		orderDAO.deleteOrder(id);
	}
	
	public void addToMenu(Food food)throws ClassNotFoundException, SQLException {
		MenuDAO menuDAO=new MenuDAO();
		 boolean success = menuDAO.insertDish(food);
		    if (success) {
		        System.out.println("\nPiatto " + food.getName() + " aggiunto al menu!");
		    }
	}
	
	public void deleteToMenu(Integer id)throws ClassNotFoundException, SQLException {
		MenuDAO menuDAO=new MenuDAO();
		boolean success= menuDAO.deleteDish(id);
		 if (success) {
		        System.out.println("\nPiatto eliminato dal menù...");
		    }
		
	}
	
	public void viewOrderList()throws ClassNotFoundException, SQLException  {
		OrderDAO orderDAO=new OrderDAO();
		orderDAO.getAllOrder();
	}
	
	public void viewMenu() throws ClassNotFoundException, SQLException {
	    MenuDAO menuDAO = new MenuDAO();
	    ArrayList<Food> dishes = menuDAO.getAllDish();
	    
	    System.out.println("  ID  |  Piatto                        | Prezzo | Descrizione");
	    System.out.println("------|--------------------------------|--------|------------------------------------");
	    
	    for (Food dish : dishes) {
	        System.out.printf(" %-5s|  %-30s| %-7.2f| %-35s%n", dish.getId(), dish.getName(), dish.getCost(), dish.getDescription());
	    }
	    
	    System.out.println("\n\n");
	}

	
	public void viewAllReservation() throws ClassNotFoundException, SQLException {
	    ReservationDAO reservationDAO = new ReservationDAO();
	    ArrayList<Reservation> reservations = reservationDAO.getAllReservation();
	    
	    System.out.println("Storico prenotazioni:");
	    System.out.println("ID prenotazione|ID cliente|       Data       |  Ospiti  |           Richiesta           ");
	    System.out.println("---------------|----------|------------------|----------|-------------------------------");
	    
	    for (Reservation reservation : reservations) {
	        String formattedDate = reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	        System.out.printf("      %-8s |    %-5s | %-16s |    %-5d | %-35s%n", 
	                          reservation.getId(), reservation.getName(), formattedDate,
	                          reservation.getNumberOfPerson(), reservation.getSpecialRequest());	
	    }
	}

	
	public void viewDailyReservation(LocalDateTime dateTime) throws ClassNotFoundException, SQLException {
	    ReservationDAO reservationDAO = new ReservationDAO();
	    ArrayList<Reservation> dailyReservations = reservationDAO.getDailyReservation(dateTime);
	    
	    System.out.println("ID prenotazione|ID cliente|       Data       |  Ospiti  |           Richiesta           ");
	    System.out.println("---------------|----------|------------------|----------|-------------------------------");
	    
	    for (Reservation reservation : dailyReservations) {
	        String formattedDate = reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	        System.out.printf("      %-8s |    %-5s | %-16s |    %-5d | %-35s%n",
	                          reservation.getId(), reservation.getName(), formattedDate,
	                          reservation.getNumberOfPerson(), reservation.getSpecialRequest());	
	    }
	}

	
	public void viewCustomerReservation(Integer id) throws ClassNotFoundException, SQLException {
	    ReservationDAO reservationDAO = new ReservationDAO();
	    ArrayList<Reservation> customerReservations = reservationDAO.getCustomerReservation(id);
	    
	    System.out.println("ID prenotazione|ID cliente|       Data       |  Ospiti  |           Richiesta           ");
	    System.out.println("---------------|----------|------------------|----------|-------------------------------");
	    
	    for (Reservation reservation : customerReservations) {
	        String formattedDate = reservation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	        System.out.printf("      %-8s |    %-5s | %-16s |    %-5d | %-35s%n",
	                          reservation.getId(), reservation.getName(), formattedDate,
	                          reservation.getNumberOfPerson(), reservation.getSpecialRequest());	
	    }
	}
	
	public void viewOrderReservation(Integer id) throws ClassNotFoundException, SQLException {
		OrderDAO orderDAO = new OrderDAO();
	    ArrayList<Order>orders=orderDAO.getReservationOrder(id);
	    MenuDAO menuDAO = new MenuDAO();

	    
	    System.out.println("   ID   |  Piatto                        |  Tavolo  |    Stato    ");
	    System.out.println("--------|--------------------------------|----------|-------------");

	    for (Order order : orders) {
	        String dishName = menuDAO.getNameDish(order.getId_food());
	        System.out.printf("   %-5s| %-30s |    %-5s | %-9s%n", order.getId(), dishName, order.getId_table(), order.isState());
	    }
	}

	
	public int bill(Integer id)throws ClassNotFoundException, SQLException {
		OrderDAO orderDAO = new OrderDAO();
		ReservationDAO reservationDAO= new ReservationDAO();
		reservationDAO.setBill(orderDAO.calculateBill(id), id);
		return orderDAO.calculateBill(id);
	}
	
	public void viewAllCustomer() throws ClassNotFoundException, SQLException {
	    CustomerDAO customerDAO = new CustomerDAO();
	    ArrayList<Customer> allCustomers = customerDAO.getAllCustomer();
	    
	    System.out.println("  ID  |     Nome     |   Cognome   |    Telefono    ");
	    System.out.println("------|--------------|-------------|----------------");
	    
	    for (Customer customer : allCustomers) {
	        System.out.printf("  %3s |  %-12s|  %-10s | %-15s %n",
	                          customer.getId(), customer.getName(), customer.getSurname(), customer.getPhone());
	    }
	}

	
	@Override
	public void update(Order order) {
		String notification ="\uD83D\uDD14";
		System.out.println("\n" + notification +" Ordine n°"+order.getId()+" pronto!!!");
	}
	
	
	public boolean checkDish(Integer id)throws ClassNotFoundException, SQLException{
		MenuDAO menuDAO= new MenuDAO();
		return menuDAO.checkItem(id);
	}
	
	public Integer getIdCustomer(Integer id)throws ClassNotFoundException, SQLException{
		ReservationDAO reservationDAO=new ReservationDAO();
		return reservationDAO.getIdCustomer(id);
	}
	
	public Integer getIdTable(Integer id)throws ClassNotFoundException, SQLException{
		ReservationDAO reservationDAO= new ReservationDAO();
		return reservationDAO.getIdTable(id);
	}
}
