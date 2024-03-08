package business_logic;

import java.sql.SQLException;

import Orm.OrderDAO;
import domain_model.Order;

public class BrigadeController {

	public void viewOrderList()throws ClassNotFoundException, SQLException  {
		OrderDAO orderDAO=new OrderDAO();
		orderDAO.getAllOrder();
	}
	
	public void markOrder(Order order)throws ClassNotFoundException, SQLException {
		OrderDAO orderDAO=new OrderDAO();
		orderDAO.modifyOrderState(order);
		order.notifyAdmin();
	}
}
