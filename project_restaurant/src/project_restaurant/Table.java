package project_restaurant;

public class Table {
	private Integer numberId;
	private Integer seatNumbers;
	private Boolean state=true;//true=libero, false=occupato

	public Table(Integer ni, Integer sn) {
		this.numberId=ni;//TODO: lanciare un eccezione se l'id è lo stesso di un altro o è invalido
		this.seatNumbers=sn;
	}

	public void infoTable() {
		System.out.println("Table n°"+getNumberId()+"\n seat numbers:"+getSeatNumbers());
		if(getState()==true)
			System.out.println("state: free.\n");
		else
			System.out.println("state: busy.\n");
	}
	
	public Integer getNumberId() {
		return numberId;
	}

	public void setNumberId(Integer numberId) {
		this.numberId = numberId;
	}

	public Integer getSeatNumbers() {
		return seatNumbers;
	}

	public void setSeatNumbers(Integer seatNumbers) {
		this.seatNumbers = seatNumbers;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}
	
	
}
