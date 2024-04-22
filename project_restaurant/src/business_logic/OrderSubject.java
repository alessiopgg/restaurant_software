package business_logic;

public interface OrderSubject {

	void attach(OrderObserver observer);
    void detach(OrderObserver observer);
    void notifyAdmin();
    
}
