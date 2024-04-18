package domain_model;

import java.sql.Date;
import java.time.LocalDateTime;// libreria per data e ora

public class Reservation {
	private Integer id;
	private LocalDateTime date;// data e ora della prenotazione
	private Integer numberOfPerson;// numero ospiti
	private String specialRequest;// nota opzionale da parte del cliente
	private Integer table;
	private Integer name;
	private Integer bill=0;

	public Reservation(Integer id, LocalDateTime d, Integer np, String sr,Integer t, Integer n) {
		this.id=id;
		this.date = d;
		this.numberOfPerson = np;
		this.specialRequest = sr;
		this.table=t;
		this.name = n;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	

	public Integer getTable() {
		return table;
	}

	public void setTable(Integer table) {
		this.table = table;
	}

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	public Integer getBill() {
		return bill;
	}

	public void setBill(Integer bill) {
		this.bill = bill;
	}
	
	
	
}