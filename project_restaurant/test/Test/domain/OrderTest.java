package Test.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import business_logic.OrderObserver;
import domain_model.Order;

public class OrderTest {
	 private Order order;
	    private ConcreteOrderObserver observer;

	    @Before
	    public void setUp() {
	        order = new Order(1, 10, 100, 5, false);
	        observer = new ConcreteOrderObserver();
	    }

	    @Test
	    public void getIdTest() {
	        assertEquals(1, (int) order.getId());
	    }

	    @Test
	    public void setIdTest() {
	        order.setId(2);
	        assertEquals(2, (int) order.getId());
	    }

	    @Test
	    public void getIdReservationTest() {
	        assertEquals(10, (int) order.getId_reservation());
	    }

	    @Test
	    public void setIdReservationTest() {
	        order.setId_reservation(20);
	        assertEquals(20, (int) order.getId_reservation());
	    }

	    @Test
	    public void getIdFoodTest() {
	        assertEquals(100, (int) order.getId_food());
	    }

	    @Test
	    public void setIdFoodTest() {
	        order.setId_food(200);
	        assertEquals(200, (int) order.getId_food());
	    }

	    @Test
	    public void getIdTableTest() {
	        assertEquals(5, (int) order.getId_table());
	    }

	    @Test
	    public void setIdTableTest() {
	        order.setId_table(10);
	        assertEquals(10, (int) order.getId_table());
	    }

	    @Test
	    public void isStateTest() {
	        assertFalse(order.isState());
	    }

	    @Test
	    public void setStateTest() {
	        order.setState(true);
	        assertTrue(order.isState());
	    }

	    @Test
	    public void attachObserverTest() {
	        order.attach(observer);
	        order.notifyAdmin();
	        assertTrue(observer.isNotified());
	    }

	    @Test
	    public void detachObserverTest() {
	        order.attach(observer);
	        order.detach(observer);
	        order.notifyAdmin();
	        assertFalse(observer.isNotified());
	    }

	    @Test
	    public void notifyAdminTest() {
	        order.attach(observer);
	        order.notifyAdmin();
	        assertTrue(observer.isNotified());
	    }

	    // Classe concreta per testare l'osservatore
	    private static class ConcreteOrderObserver implements OrderObserver {
	        private boolean notified = false;

	        @Override
	        public void update(Order order) {
	            this.notified = true;
	        }

	        public boolean isNotified() {
	            return notified;
	        }
	    }
}
