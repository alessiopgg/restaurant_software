package business_logic;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Orm.CustomerDAO;
import Orm.MenuDAO;
import Orm.ReservationDAO;
import Orm.TableDAO;
import domain_model.Customer;
import domain_model.Reservation;
import domain_model.Table;

public class CustomerController {

	public void signIn(Customer customer)throws ClassNotFoundException, SQLException {
		CustomerDAO customerDAO= new CustomerDAO();
		if(customerDAO.checkNumberPhone(customer)==false) {
			customerDAO.addCustomer(customer);
            System.out.println("Registrazione avvenuta con successo!\n");

		}else {
			System.out.println("Phone number alredy used...\n");
		}
	}
	
	public boolean login(Customer customer)throws ClassNotFoundException, SQLException {
		CustomerDAO customerDAO= new CustomerDAO();
		if(customerDAO.findClient(customer)==true) {
			System.out.println("Login effettuato con successo!\n");
			return true;
		}else {
			System.out.println("Impossibile effettuare il login...\n");
			return false;
		}
	}
	
	public void deleteAccount(Customer customer)throws ClassNotFoundException, SQLException {
		CustomerDAO customerDAO= new CustomerDAO();
		if(customerDAO.findClient(customer)==true) {
			customerDAO.deleteCustomer(customer.getId());
		}else System.out.println("Account not found...");
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
	public void viewMyReservation(Customer customer) throws ClassNotFoundException, SQLException {
	    ReservationDAO reservationDAO = new ReservationDAO();

	    System.out.println("\n\nPrenotazioni attive:");
	    System.out.println("   ID   |       Data       |  Ospiti  |           Richiesta           ");
	    System.out.println("--------|------------------|----------|-------------------------------");

	    for (int i=0; i<reservationDAO.getCustomerReservation(customer.getId()).size();i++) {
	        System.out.printf("   %-5s| %-16s |    %-5d | %-35s%n",reservationDAO.getCustomerReservation(customer.getId()).get(i).getId() ,
	        		reservationDAO.getCustomerReservation(customer.getId()).get(i).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
	        		reservationDAO.getCustomerReservation(customer.getId()).get(i).getNumberOfPerson(), reservationDAO.getCustomerReservation(customer.getId()).get(i).getSpecialRequest());
	    }
	}

	
	public void newReservation(Reservation reservation)throws ClassNotFoundException, SQLException{
		ReservationDAO reservationDAO=new ReservationDAO();
		TableDAO tableDAO=new TableDAO();
		
		int n_busy=0;
		for(int i=0; i<reservationDAO.getTableDate(reservation.getDate()).size();i++) {
			n_busy+=reservationDAO.getTableDate(reservation.getDate()).get(i).getSeatNumbers();
		}
		if((tableDAO.getTotalSeat()-n_busy)>=reservation.getNumberOfPerson()) {
			ArrayList<Table>freeTable=new ArrayList<>();
			for(int i=0; i<tableDAO.getAllTable().size();i++) {
				if(reservationDAO.getTableDate(reservation.getDate()).contains(tableDAO.getAllTable().get(i))) {
					freeTable.add(tableDAO.getAllTable().get(i));
				}
			}
			reservation.setTable(tableDAO.getAllTable().get(0).getId());
			reservationDAO.insertReservation(reservation);
			System.out.println("Booking made!");

		}
		else System.out.println("Booking not available for these dates...");

	}
	
	
public boolean checkMyReservation(Integer idReservation,Customer c) throws ClassNotFoundException, SQLException{
	ReservationDAO reservationDAO= new ReservationDAO();
	
	for(int i=0; i<reservationDAO.getCustomerReservation(c.getId()).size();i++) {
		if(reservationDAO.getCustomerReservation(c.getId()).get(i).getName()==c.getId())
			return true;
	}
	return false;
	}
	
	public void deleteReservation(Integer id)throws ClassNotFoundException, SQLException{
		ReservationDAO reservationDAO= new ReservationDAO();
		reservationDAO.deleteReservation(id);
	}
	
	public Integer getId(String phone)throws ClassNotFoundException, SQLException{
		CustomerDAO customerDAO= new CustomerDAO();
		return customerDAO.getId(phone);
	}
	
	
}
