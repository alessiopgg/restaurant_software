package domain_model;

public class Customer {
	private Integer id;
	private String surname;
	private String name;
	private String phone;
	
	public Customer(Integer id, String surname, String name, String phone) {
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.phone = phone;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
