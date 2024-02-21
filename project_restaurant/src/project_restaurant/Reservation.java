package project_restaurant;

import java.time.LocalDateTime;// libreria per data e ora

public class Reservation {
	private String name;
	private String phoneNumber;
	private Integer numberOfPerson;// numero ospiti
	private LocalDateTime date;// data e ora della prenotazione
	private String specialRequest;// nota opzionale da parte del cliente

	public Reservation(String n, String pn, Integer np, LocalDateTime d, String sr) {
		this.name = n;
		this.phoneNumber = pn;
		this.numberOfPerson = np;
		this.date = d;
		this.specialRequest = sr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getNumberOfPerson() {
		return numberOfPerson;
	}

	public void setNumberOfPerson(Integer numberOfPerson) {
		this.numberOfPerson = numberOfPerson;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getSpecialRequest() {
		return specialRequest;
	}

	public void setSpecialRequest(String specialRequest) {
		this.specialRequest = specialRequest;
	}

}
