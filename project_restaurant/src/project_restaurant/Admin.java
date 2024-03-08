package project_restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Orm.DatabaseConnect;
import domain_model.Customer;

import java.time.LocalDate;

public class Admin {
	private DatabaseConnect databaseManager;
	
	 public Admin(DatabaseConnect db) {
		 this.databaseManager=db;
	}
	 
	 public void addCustomer(Customer c) {
		 databaseManager.singIn(c.getSurname() ,c.getName(), c.getPhone());
	 }
	 
	 
	 
	
	
}
