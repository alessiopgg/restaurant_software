package user_interface;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import Orm.DatabaseConnect;
import Orm.ReservationDAO;
import business_logic.AdminController;
import business_logic.BrigadeController;
import business_logic.CustomerController;
import domain_model.Customer;
import domain_model.Food;
import domain_model.Order;
import domain_model.Reservation;
import domain_model.Table;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);
        
        DatabaseConnect.getConnection();
        
        // Inizializza i controller
        AdminController adminController = new AdminController();
        BrigadeController brigadeController = new BrigadeController();
        CustomerController customerController = new CustomerController();
        
        System.out.println("Benvenuto al sistema di gestione del ristorante!");
        
        while (true) {
            System.out.println("Seleziona un'opzione:");
            System.out.println("1. Cliente");
            System.out.println("2. Brigata di cucina");
            System.out.println("3. Amministratore");
            System.out.println("4. Esci");

            System.out.print("--> ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consuma il carattere newline dopo nextInt()

            switch (choice) {
                case 1:
                    customerMenu(scanner, customerController);
                    break;
                case 2:
                    brigadeMenu(scanner, brigadeController);
                    break;
                case 3:
                    adminMenu(scanner, adminController);
                    break;
                case 4:
                    System.out.println("Arrivederci!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.\n");
            }
        }
    }

	public static void customerMenu(Scanner scanner, CustomerController customerController)
			throws ClassNotFoundException, SQLException {
		while (true) {
			System.out.println("\n\nMenu cliente:");
			System.out.println("1. Registrati");
			System.out.println("2. Effettua il login");
			System.out.println("3. Torna indietro"); 
			
			System.out.print("--> ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consuma il carattere newline dopo nextInt()
			System.out.println("\n");

			switch (choice) {
			case 1:
				// Registrazione
				System.out.println("\nRegistrazione nuovo profilo...");

				System.out.print("Inserisci il tuo cognome:");
				String surname = scanner.nextLine();
				System.out.print("Inserisci il tuo nome:");
				String name = scanner.nextLine();
				System.out.print("Inserisci il tuo numero di telefono:");
				String phone = scanner.nextLine();
				Customer customer = new Customer(null, surname, name, phone);

				customerController.signIn(customer);

				customer.setId(customerController.getId(phone));
				break;
			case 2:
				// Login
				System.out.println("\nEffettua login...");
				System.out.print("Inserisci il tuo cognome:");
				String loginSurname = scanner.nextLine();
				System.out.print("Inserisci il tuo numero di telefono:");
				String loginPhone = scanner.nextLine();
				Customer loginCustomer = new Customer(customerController.getId(loginPhone), loginSurname, null,
						loginPhone);

				if (customerController.login(loginCustomer)) {
					// Qui puoi aggiungere altre azioni dopo il login

					while (true) {

						System.out.println("1. Visualizza il menu");
						System.out.println("2. Visualizza le tue prenotazioni");
						System.out.println("3. Effettua una nuova prenotazione");
						System.out.println("4. Cancella una prenotazione");
						System.out.println("5. Torna indietro");

						choice = scanner.nextInt();
						scanner.nextLine();

						switch (choice) {
						case 1:
							// Visualizza il menu
							customerController.viewMenu();
							break;
						case 2:

							customerController.viewMyReservation(loginCustomer);
							break;

						case 3:
							// Effettua una nuova prenotazione
							System.out.println(
									"Inserisci la data e l'ora della prenotazione (formato: yyyy-MM-dd HH:mm):");
							String dateTimeStr = scanner.nextLine();

							try {
								LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr,
										DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

								System.out.println("Inserisci il numero di persone:");
								int numberOfPersons = scanner.nextInt();
								scanner.nextLine(); // Consuma il carattere newline dopo nextInt()

								System.out.println("Inserisci eventuali richieste speciali:");
								String specialRequests = scanner.nextLine();

								Reservation newReservation = new Reservation(null, dateTime, numberOfPersons,
										specialRequests,null, loginCustomer.getId());

								customerController.newReservation(newReservation);
								System.out.println("Prenotazione effettuata con successo!");
							} catch (DateTimeParseException e) {
								System.out.println("Formato data e ora non valido. Riprova.");
							} catch (Exception e) {
								System.out.println("Errore durante la prenotazione: " + e.getMessage());
							}
							break;

						case 4:
							// Cancella una prenotazione
							customerController.viewMyReservation(loginCustomer);
							System.out.print("\nDigita l'ID della prenotazione che vuoi eliminare: ");
							int id = scanner.nextInt();
							scanner.nextLine();
							if (customerController.checkMyReservation(id, loginCustomer) == true) {
								System.out.println("Vuoi davvero eliminare la prenotazione n°" + id
										+ "?\nDigita:\n0. Annulla\n1. Conferma");
								int d = scanner.nextInt();
								scanner.nextLine();
								if (d == 1) {
									customerController.deleteReservation(id);
									System.out.println("Prenotazione cancellata!");
								} else
									System.out.println("Eliminazione interrotta...");
							}
							break;
						case 5:
							// Torna indietro
							return;
						default:
							System.out.println("Scelta non valida. Riprova.");
						}
					}

				} else {
					System.out.println("Login fallito: cliente non trovato.");
				}

				break;
			case 3:
				// Torna indietro
				return;
			default:
				System.out.println("Scelta non valida. Riprova.");
			}

		}
	}

    public static void brigadeMenu(Scanner scanner, BrigadeController brigadeController) throws ClassNotFoundException, SQLException {
        while (true) {
            System.out.println("\n\nMenu brigata di cucina:");
            System.out.println("1. Visualizza la lista degli ordini");
            System.out.println("2. Segna un ordine come completato");
            System.out.println("3. Torna indietro");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consuma il carattere newline dopo nextInt()

            switch (choice) {
                case 1:
                    // Visualizza la lista degli ordini
                  
                     brigadeController.viewOrderList();
                   
                    break;
                case 2:
                    // Segna un ordine come completato
                	System.out.print("Digita l'ID dell'ordine completato: ");
                	int id= scanner.nextInt();
                	scanner.nextLine();
                	brigadeController.markOrder(id);

                	break;
                case 3:
                    // Torna indietro
                    return;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    public static void adminMenu(Scanner scanner, AdminController adminController) throws ClassNotFoundException, SQLException {
        while (true) {
            System.out.println("\n\nMenu amministratore:");
            System.out.println("1. Aggiungi un piatto al menu");
            System.out.println("2. Rimuovi un piatto dal menu");
            System.out.println("3. Visualizza il menu");
            System.out.println("4. Visualizza tutte le prenotazioni");
            System.out.println("5. Visualizza le prenotazioni di oggi");
            System.out.println("6. Visualizza le prenotazioni di un cliente");
            System.out.println("7. Calcola il conto di una prenotazione");
            System.out.println("8. Crea un ordine");
            System.out.println("9. Elimina un ordine");
            System.out.println("10. Torna indietro");
            System.out.print("--> ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consuma il carattere newline dopo nextInt()

            System.out.print("\n");
            switch (choice) {
                case 1:
                    // Aggiungi un piatto al menu
                	System.out.print("Digita il nome del piatto: ");
                	String nameDish=scanner.nextLine();                	
                	System.out.print("Inserisci una descrizione del piatto: ");
                	String description= scanner.nextLine();
                	System.out.print("Inserisci il costo per il piatto: ");
                	Double cost=scanner.nextDouble();
                	scanner.nextLine();
                	Food dish=new Food(null, nameDish, cost, description);
                	adminController.addToMenu(dish);
                    break;
                case 2:
                    // Rimuovi un piatto dal menu
                	adminController.viewMenu();
                	System.out.print("Inserisci ID del piatto da rimuove dal menu': ");
                	Integer n=scanner.nextInt();
                	scanner.nextLine();
                	adminController.deleteToMenu(n);
                    break;
                case 3:
                    // Visualizza il menu
                     adminController.viewMenu();
                   
                    break;
                case 4:
                    // Visualizza tutte le prenotazioni
                	adminController.viewAllReservation();

                	break;
                case 5:
                    // Visualizza le prenotazioni di oggi
                	System.out.print("Digita la data delle prenotazioni da visualizzare (formato: yyyy-MM-dd): ");
                	String dateStr = scanner.nextLine();
                	try {
                	    LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                	    adminController.viewDailyReservation(date.atStartOfDay()); //Imposta l'ora a mezzanotte (00:00:00)
                	} catch (DateTimeParseException e) {
                	    e.printStackTrace();
                	}
                	break;
                case 6:
                	adminController.viewAllCustomer();
                    System.out.print("\nInserisci l'id del cliente di cui si vuole visualizzare le prenotazioni: ");
                    Integer id=scanner.nextInt();
                    scanner.nextLine();
                    adminController.viewCustomerReservation(id);
                    break;
                case 7:
                    // Calcola il conto di una 
                	System.out.print("Inserisci ID della prenotazione di cui si vuole calcolare il conto: ");
                	Integer idP=scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Conto totale: "+adminController.bill(idP));
                    // Implementa la logica per calcolare il conto di una prenotazione
                    break;
                case 8:
                	System.out.print("Inserisci ID della prenotazione per associargli un ordine: ");
                	idP=scanner.nextInt();
                    scanner.nextLine();
                    int idT=adminController.getIdTable(idP);
                    adminController.viewMenu();
                    while(true) {
                    	System.out.print("Inserisci ID di un piatto alla volta o premi 0 per confermare l'ordine: ");
                    	int idD=scanner.nextInt();
                    	scanner.nextLine();
                    	if(idD!=0) {
                    		if(adminController.checkDish(idD)) {
                    			Order order=new Order(null, idP, idD, idT , false);
                    			adminController.createOrder(order);
                    		}else
                    			System.out.println("L'ID del piatto non è corretto...\n");
                    	}if(idD==0) {
                    		System.out.println("Ordine terminato!");
                    		break;
                    		}
                    }
                    break;
                case 9:
                	System.out.print("Digita l'ID dell'ordine da eliminare: ");
                	int idO=scanner.nextInt();
                	scanner.nextLine();
                	adminController.viewOrderList();
                	adminController.deleteOrder(idO);
                	
                	break;
                case 10:
                    // Torna indietro
                    return;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }
}
