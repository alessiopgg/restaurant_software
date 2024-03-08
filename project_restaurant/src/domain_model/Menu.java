package domain_model;

import java.util.ArrayList;

public class Menu {

	private ArrayList<Food>menù;
	
	public Menu() {
		
	}

	public ArrayList<Food> getMenù() {
		return menù;
	}

	public void setMenù(ArrayList<Food> menù) {
		this.menù = menù;
	}
	
}
