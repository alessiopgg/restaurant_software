package project_restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class Admin {
	 Map<LocalDate, ArrayList<Reservation>> reservationMap = new HashMap<>();
	 ArrayList<Table>tableList=new ArrayList<>();
	
	 public Admin(Map<LocalDate, ArrayList<Reservation>> reservationMap, ArrayList<Table> tableList) {
		 
		this.reservationMap = reservationMap;
		this.tableList = tableList;
		
	}
	 
	 
	
	
}
