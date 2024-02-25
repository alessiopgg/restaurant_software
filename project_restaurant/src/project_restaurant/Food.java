package project_restaurant;

public class Food {
	private String name;
	private String description;
	private Double cost;
	
	public Food(String name, String description, Double cost) {
		this.name = name;
		this.description = description;
		this.cost=cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	 
	
	
}
