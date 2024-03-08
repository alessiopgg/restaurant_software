package domain_model;

public class Order {
	private String id;
	private String id_customer;
	private String id_reservation;
	private String id_food;
	private String id_table;
	private boolean state=false;
	
	
	public Order(String id, String id_customer, String id_reservation, String id_food, String id_table, boolean state) {
		this.id = id;
		this.id_customer = id_customer;
		this.id_reservation = id_reservation;
		this.id_food = id_food;
		this.id_table = id_table;
		this.state = state;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getId_customer() {
		return id_customer;
	}


	public void setId_customer(String id_customer) {
		this.id_customer = id_customer;
	}


	public String getId_reservation() {
		return id_reservation;
	}


	public void setId_reservation(String id_reservation) {
		this.id_reservation = id_reservation;
	}


	public String getId_food() {
		return id_food;
	}


	public void setId_food(String id_food) {
		this.id_food = id_food;
	}


	public String getId_table() {
		return id_table;
	}


	public void setId_table(String id_table) {
		this.id_table = id_table;
	}


	public boolean isState() {
		return state;
	}


	public void setState(boolean state) {
		this.state = state;
	}
	
	

}
