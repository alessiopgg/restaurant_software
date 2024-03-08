package project_restaurant;

import java.util.Scanner;

import Orm.DatabaseConnect;
import domain_model.Table;

public class Main {

	public static void main(String[] args) {
		
		DatabaseConnect sqlConnect = new DatabaseConnect(); 
		sqlConnect.connect();
		
		Table[]table=new Table[30];
		for(int i=0;i<5;i++) {
			table[i]=new Table(i+1,4);
			for(int j=1;j<6;j++){
			table[j+i]=new Table(i+1+j,2);
			}
		}

		Scanner scanner=new Scanner(System.in);
		while(true) {
			
		System.out.print("Select the access method:\n1.Customer\n2.Business\nInsert the number: ");
		String number=scanner.nextLine();
		
		switch(number) {
		case "1":
			System.out.println("Logger as custumer...\n");
			
			//Scanner scanner1=new Scanner(System.in);
			System.out.print("Choice:\n1.Login\n2.Sign in\nInsert the number: ");
			number=scanner.nextLine();

			switch(number) {
			case "1"://login
				login(sqlConnect);
				break;
			case "2"://crea nuovo account cliente
				System.out.print(sqlConnect.singIn("Poggesi", "Alessio", "+39 377 5329260"));
				
				break;
			}
			break;
		case "2":
			System.out.println("Access to business mode...\n");
			
			break;
		default:
			System.out.println("Invalid choice, please try again...");
			break;

		}
		}
	}
	
	public static void login(DatabaseConnect sqlConnect) {
		Scanner scanner=new Scanner(System.in);
		while(true) {
			System.out.println("Insert surname: ");
			String s=scanner.nextLine();
			System.out.println("Insert phone number: ");
			String t=scanner.nextLine();
			if(sqlConnect.findClient(s, t)==true) {
				System.out.println("Login completed!");
			    return;
			}
			else {
				System.out.println("Account not found...");
			}
		}
	}
}
