package domain_model;

import java.time.LocalDateTime;// libreria per data e ora

public class Reservation {
	private String id;
	private LocalDateTime date;// data e ora della prenotazione
	private Integer numberOfPerson;// numero ospiti
	private String specialRequest;// nota opzionale da parte del cliente
	private String name;
	private Integer bill=0;

	public Reservation(String id, LocalDateTime d, Integer np, String sr, String n) {
		this.id=id;
		this.date = d;
		this.numberOfPerson = np;
		this.specialRequest = sr;
		this.name = n;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Integer getNumberOfPerson() {
		return numberOfPerson;
	}

	public void setNumberOfPerson(Integer numberOfPerson) {
		this.numberOfPerson = numberOfPerson;
	}

	public String getSpecialRequest() {
		return specialRequest;
	}

	public void setSpecialRequest(String specialRequest) {
		this.specialRequest = specialRequest;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBill() {
		return bill;
	}

	public void setBill(Integer bill) {
		this.bill = bill;
	}
	
	
	
}