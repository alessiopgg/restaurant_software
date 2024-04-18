package business_logic;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Orm.CustomerDAO;
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
		order.setState(false);
		orderDAO.insertOrder(order);
		
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
	
	public void viewMenu()throws ClassNotFoundException, SQLException {
		MenuDAO menuDAO=new MenuDAO();
		System.out.println("  ID  |  Piatto                        | Prezzo | Descrizione");
	        System.out.println("------|--------------------------------|--------|------------------------------------");
	        for(int i=0; i<menuDAO.getAllDish().size(); i++) {
	        	System.out.printf(" %-5s|  %-30s| %-7.2f| %-35s%n", menuDAO.getAllDish().get(i).getId(),menuDAO.getAllDish().get(i).getName(),menuDAO.getAllDish().get(i).getCost(),menuDAO.getAllDish().get(i).getDescription());	
		}
	        System.out.println("\n\n");
	}
	
	public void viewAllReservation()throws ClassNotFoundException, SQLException {
		ReservationDAO reservationDAO=new ReservationDAO();
		System.out.println("Storico prenotazioni:");
		System.out.println("ID prenotazione|ID cliente|       Data       |  Ospiti  |           Richiesta           ");
	    System.out.println("---------------|----------|------------------|----------|-------------------------------");
	    for(int i=0; i<reservationDAO.getAllReservation().size();i++) {
	    	System.out.printf("      %-8s |    %-5s | %-16s |    %-5d | %-35s%n",reservationDAO.getAllReservation().get(i).getId(),
	        		reservationDAO.getAllReservation().get(i).getName(),reservationDAO.getAllReservation().get(i).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 
	        		reservationDAO.getAllReservation().get(i).getNumberOfPerson(), reservationDAO.getAllReservation().get(i).getSpecialRequest());	    	
	    };
	}
	
	public void viewDailyReservation(LocalDateTime dateTime)throws ClassNotFoundException, SQLException {
		ReservationDAO reservationDAO=new ReservationDAO();
	System.out.println("ID prenotazione|ID cliente|       Data       |  Ospiti  |           Richiesta           ");
    System.out.println("---------------|----------|------------------|----------|-------------------------------");
    for(int i=0; i<reservationDAO.getDailyReservation(dateTime).size();i++) {
    	System.out.printf("      %-8s |    %-5s | %-16s |    %-5d | %-35s%n",reservationDAO.getDailyReservation(dateTime).get(i).getId() ,
    			reservationDAO.getDailyReservation(dateTime).get(i).getName(),
        		reservationDAO.getDailyReservation(dateTime).get(i).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        		reservationDAO.getDailyReservation(dateTime).get(i).getNumberOfPerson(), reservationDAO.getAllReservation().get(i).getSpecialRequest());	    	
    };
	}
	
	public void viewCustomerReservation(Integer id)throws ClassNotFoundException, SQLException {
		ReservationDAO reservationDAO=new ReservationDAO();
		reservationDAO.getCustomerReservation(id);
		System.out.println("ID prenotazione|ID cliente|       Data       |  Ospiti  |           Richiesta           ");
	    System.out.println("---------------|----------|------------------|----------|-------------------------------");
	    for(int i=0; i<reservationDAO.getCustomerReservation(id).size();i++) {
	    	System.out.printf("      %-8s |    %-5s | %-16s |    %-5d | %-35s%n",reservationDAO.getCustomerReservation(id).get(i).getId() ,
	    			reservationDAO.getCustomerReservation(id).get(i).getName(),
	        		reservationDAO.getCustomerReservation(id).get(i).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
	        		reservationDAO.getCustomerReservation(id).get(i).getNumberOfPerson(), reservationDAO.getCustomerReservation(id).get(i).getSpecialRequest());	    	
	    };
		}
	
	public int bill(Integer id)throws ClassNotFoundException, SQLException {
		OrderDAO orderDAO = new OrderDAO();
		ReservationDAO reservationDAO= new ReservationDAO();
		reservationDAO.setBill(orderDAO.calculateBill(id), id);
		return orderDAO.calculateBill(id);
	}
	
	public void viewAllCustomer()throws ClassNotFoundException, SQLException {
		CustomerDAO customerDAO=new CustomerDAO();
		System.out.println("  ID  |     Nome     |   Cognome   |    Telefono    ");
	    System.out.println("------|--------------|-------------|----------------");
        for(int i=0;i<customerDAO.getAllCustomer().size();i++) {
	    	System.out.printf("  %3s |  %-12s|  %-10s | %-15s %n",customerDAO.getAllCustomer().get(i).getId(),
	    			customerDAO.getAllCustomer().get(i).getName(),
	    			customerDAO.getAllCustomer().get(i).getSurname(),customerDAO.getAllCustomer().get(i).getPhone());
        }
	}
	
	public void update(Order order) {
		System.out.println(order.getId()+" is ready...");
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
