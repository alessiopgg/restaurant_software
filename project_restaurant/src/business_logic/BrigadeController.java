package business_logic;

import java.sql.SQLException;
import java.util.ArrayList;

import Orm.MenuDAO;
import Orm.OrderDAO;
import domain_model.Order;

public class BrigadeController {

	public void viewOrderList() throws ClassNotFoundException, SQLException {
	    OrderDAO orderDAO = new OrderDAO();
	    MenuDAO menuDAO = new MenuDAO();
	    ArrayList<Order> allOrders = orderDAO.getAllOrder();

	    System.out.println("   ID   |  Piatto                        |  Tavolo  |    Stato    ");
	    System.out.println("--------|--------------------------------|----------|-------------");

	    for (Order order : allOrders) {
	        String dishName = menuDAO.getNameDish(order.getId_food());
	        System.out.printf("   %-5s| %-30s |    %-5s | %-9s%n", order.getId(), dishName, order.getId_table(), order.isState());
	    }
	}

	
	public void markOrder(Integer id, AdminController controller)throws ClassNotFoundException, SQLException {
		OrderDAO orderDAO=new OrderDAO();
		Order order= orderDAO.getOrder(id);
		order.attach(controller);
		orderDAO.modifyOrderState(id);
		order.notifyAdmin();
		
	}
	
	public void viewOrderUncompleted() throws ClassNotFoundException, SQLException {
	    OrderDAO orderDAO = new OrderDAO();
	    MenuDAO menuDAO = new MenuDAO();
	    ArrayList<Order> uncompletedOrders = orderDAO.getUncompletedOrder();

	    if (uncompletedOrders.isEmpty()) {
	        System.out.println("Nessun ordine non completato trovato.");
	        return;
	    }

	    System.out.println("   ID   |  Piatto                        |  Tavolo  |    Stato    ");
	    System.out.println("--------|--------------------------------|----------|-------------");

	    for (Order order : uncompletedOrders) {
	        String dishName = menuDAO.getNameDish(order.getId_food());
	        System.out.printf("   %-5s| %-30s |    %-5s | %-9s%n", order.getId(), dishName, order.getId_table(), order.isState());
	    }
	}

	
	public void viewOrderOptimized() throws ClassNotFoundException, SQLException  {
	    OrderDAO orderDAO = new OrderDAO();
	    MenuDAO menuDAO = new MenuDAO();
	    
	    ArrayList<Order> allOrders = orderDAO.getAllOrder();
	    
	    System.out.println("   ID   |  Piatto                        |  Tavolo  |    Stato    ");
	    System.out.println("--------|--------------------------------|----------|-------------");
	    
	    for (Order order : allOrders) {
	        if (order.isState()) {
	            System.out.printf("   %-5s| %-30s |    %-5s | %-9s%n", order.getId(),
	    	            menuDAO.getNameDish(order.getId_food()), order.getId_table(), order.isState());
	        }
	    }
	    
	    for (Order order : allOrders) {
	        if (!order.isState()) {
	            System.out.printf("   %-5s| %-30s |    %-5s | %-9s%n", order.getId(),
	    	            menuDAO.getNameDish(order.getId_food()), order.getId_table(), order.isState());
	        }
	    }
	}

	
}

