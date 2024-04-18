package business_logic;

import java.sql.SQLException;

import Orm.MenuDAO;
import Orm.OrderDAO;
import domain_model.Order;

public class BrigadeController {

	public void viewOrderList()throws ClassNotFoundException, SQLException  {
		OrderDAO orderDAO=new OrderDAO();
		MenuDAO menuDAO=new MenuDAO();
		
		System.out.println("   ID   |  Piatto                        |  Tavolo  |    Stato    ");
	    System.out.println("--------|--------------------------------|----------|-------------");
		
		for(int i=0;i<orderDAO.getAllOrder().size();i++) {
			System.out.printf("   %-5s| %-30s |    %-5s | %-9s%n",orderDAO.getAllOrder().get(i).getId(),
					menuDAO.getNameDish(orderDAO.getAllOrder().get(i).getId_food()),orderDAO.getAllOrder().get(i).getId_table(),
					orderDAO.getAllOrder().get(i).isState());
		}
	}
	
	public void markOrder(Integer id)throws ClassNotFoundException, SQLException {
		OrderDAO orderDAO=new OrderDAO();
		orderDAO.modifyOrderState(id);
		
	}
}

