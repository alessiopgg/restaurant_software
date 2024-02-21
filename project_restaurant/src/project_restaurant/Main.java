package project_restaurant;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Table[]table=new Table[30];
		for(int i=0;i<5;i++) {
			table[i]=new Table(i+1,4);
			for(int j=1;j<6;j++){
			table[j+i]=new Table(i+1+j,2);
			}
		}
		
		
		Scanner scanner=new Scanner(System.in);
		System.out.print("Select the access method:\n1.Customer\n2.Business\nInsert the number: ");
		String number=scanner.nextLine();
		scanner.close();
		
		switch(number) {
		case "1":
			System.out.println("Logger as custumer...");
			break;
		case "2":
			System.out.println("Access to business mode...");
			break;
		default:
			System.out.println("Invalid choice, please try again...");
			break;

		}
		
		
		
	}

}
