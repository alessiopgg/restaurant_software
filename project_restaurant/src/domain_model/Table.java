package domain_model;

public class Table {
	private Integer id;
	private Integer number;
	private Integer seatNumbers;

	public Table(Integer id,Integer number, Integer sn) {
		this.id=id;
		this.number=number;
		this.seatNumbers=sn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSeatNumbers() {
		return seatNumbers;
	}

	public void setSeatNumbers(Integer seatNumbers) {
		this.seatNumbers = seatNumbers;
	}

}
