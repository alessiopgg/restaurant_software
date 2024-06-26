package domain_model;

public class Food {
	private Integer id;
	private String name;
	private String description;
	private Double cost;
	
	public Food(Integer id,String name, Double cost, String description) {
		this.id=id;
		this.name = name;
		this.description = description;
		this.cost=cost;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	@Override
	public String toString() {
	    return "Food[id=" + id + ", name=" + name + ", cost=" + cost + ", description=" + description + "]";
	}
	
}
