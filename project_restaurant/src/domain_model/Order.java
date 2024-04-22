package domain_model;

import business_logic.OrderObserver;
import business_logic.OrderSubject;

import java.util.ArrayList;
import java.util.List;


public class Order implements OrderSubject{
	private Integer id;
	private Integer id_reservation;
	private Integer id_food;
	private Integer id_table;
	private boolean state;
    private List<OrderObserver> observers = new ArrayList<>();

	
	
	public Order(Integer id, Integer id_reservation, Integer id_food, Integer id_table, boolean state) {
		this.id = id;
		this.id_reservation = id_reservation;
		this.id_food = id_food;
		this.id_table = id_table;
		this.state = state;

	}
	
	 // Metodo per registrare gli osservatori
    @Override
    public void attach(OrderObserver observer) {
        observers.add(observer);
    }

    // Metodo per rimuovere gli osservatori
    @Override
    public void detach(OrderObserver observer) {
        observers.remove(observer);
    }

    // Metodo per notificare l'admin
    @Override
    public void notifyAdmin() {
        for (OrderObserver observer : observers) {
            observer.update(this);
        }
    }
    
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getId_reservation() {
		return id_reservation;
	}


	public void setId_reservation(Integer id_reservation) {
		this.id_reservation = id_reservation;
	}


	public Integer getId_food() {
		return id_food;
	}


	public void setId_food(Integer id_food) {
		this.id_food = id_food;
	}


	public Integer getId_table() {
		return id_table;
	}


	public void setId_table(Integer id_table) {
		this.id_table = id_table;
	}


	public boolean isState() {
		return state;
	}


	public void setState(boolean state) {
		this.state = state;
	}
	
	

}
