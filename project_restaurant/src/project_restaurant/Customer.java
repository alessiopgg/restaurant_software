package project_restaurant;

import java.time.LocalDateTime;

public class Customer {
	private Reservation reservation;
	private String id;
	
	public Customer(String id) {
		this.id=id;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
