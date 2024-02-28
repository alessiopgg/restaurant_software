package project_restaurant;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Order {
	private ArrayList<Food> order =new ArrayList<>();
	private Reservation reservation;
	private Table table;
	private Integer bill;
	
	
	public Order(Reservation reservation, Table table, Integer bill) {
		this.reservation = reservation;
		this.table = table;
		this.bill = bill;
	}
	
	
	public void addFood(Food f) {
		order.add(f);	
	}
	
	
	public void removeFood(Food f) {
		try {
			order.remove(f);
		}
		catch (NoSuchElementException e) {
	        // Gestisci l'eccezione NoSuchElement (se l'elemento non è presente nella lista)
	        e.printStackTrace(); // Stampa lo stack trace dell'eccezione
	    }
	}


	public Reservation getReservation() {
		return reservation;
	}


	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}


	public Table getTable() {
		return table;
	}


	public void setTable(Table table) {
		this.table = table;
	}


	public Integer getBill() {
		return bill;
	}


	public void setBill(Integer bill) {
		this.bill = bill;
	}
	
	
	
	

}
