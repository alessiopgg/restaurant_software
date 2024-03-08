package business_logic;

import domain_model.Order;

public interface OrderObserver {

	void update(Order order);
}
