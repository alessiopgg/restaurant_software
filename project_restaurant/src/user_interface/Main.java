package user_interface;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Orm.DatabaseConnect;
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

        while (true) {
            System.out.println("Benvenuto al sistema di gestione del ristorante!");
            System.out.println("Seleziona un'opzione:");
            System.out.println("1. Cliente");
            System.out.println("2. Brigata di cucina");
            System.out.println("3. Amministratore");
            System.out.println("4. Esci");


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
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    public static void customerMenu(Scanner scanner, CustomerController customerController) {
        while (true) {
            System.out.println("Menu cliente:");
            System.out.println("1. Registrati");
            System.out.println("2. Effettua il login");
            System.out.println("3. Visualizza il menu");
            System.out.println("4. Visualizza le tue prenotazioni");
            System.out.println("5. Effettua una nuova prenotazione");
            System.out.println("6. Cancella una prenotazione");
            System.out.println("7. Torna indietro");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consuma il carattere newline dopo nextInt()

            switch (choice) {
                case 1:
                    // Registrazione
                    System.out.println("Inserisci il tuo cognome:");
                    String surname = scanner.nextLine();
                    System.out.println("Inserisci il tuo nome:");
                    String name = scanner.nextLine();
                    System.out.println("Inserisci il tuo numero di telefono:");
                    String phone = scanner.nextLine();
                    Customer customer = new Customer(null, surname, name, phone);
                    try {
                        customerController.signIn(customer);
                        System.out.println("Registrazione avvenuta con successo!");
                    } catch (Exception e) {
                        System.out.println("Errore durante la registrazione: " + e.getMessage());
                    }
                    break;
                case 2:
                    // Login
                    System.out.println("Inserisci il tuo cognome:");
                    String loginSurname = scanner.nextLine();
                    System.out.println("Inserisci il tuo numero di telefono:");
                    String loginPhone = scanner.nextLine();
                    Customer loginCustomer = new Customer(null, loginSurname, null, loginPhone);
                    try {
                        if (customerController.login(loginCustomer)) {
                            System.out.println("Login effettuato con successo!");
                            // Qui puoi aggiungere altre azioni dopo il login
                        } else {
                            System.out.println("Login fallito: cliente non trovato.");
                        }
                    } catch (Exception e) {
                        System.out.println("Errore durante il login: " + e.getMessage());
                    }
                    break;
                case 3:
                    // Visualizza il menu
                    try {
                        customerController.viewMenu();
                    } catch (Exception e) {
                        System.out.println("Errore durante il recupero del menu: " + e.getMessage());
                    }
                    break;
                case 4:
                    // Visualizza le prenotazioni del cliente
                    System.out.println("Inserisci il tuo ID cliente:");
                    String customerId = scanner.nextLine();
                    Customer customerInfo = new Customer(customerId, null, null, null);
                    try {
                        customerController.viewMyReservation(customerInfo);
                    } catch (Exception e) {
                        System.out.println("Errore durante il recupero delle prenotazioni: " + e.getMessage());
                    }
                    break;
                case 5:
                    // Effettua una nuova prenotazione
                    System.out.println("Inserisci la data e l'ora della prenotazione (formato: yyyy-MM-dd HH:mm):");
                    String dateTimeStr = scanner.nextLine();
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    System.out.println("Inserisci il numero di persone:");
                    int numberOfPersons = scanner.nextInt();
                    scanner.nextLine(); // Consuma il carattere newline dopo nextInt()
                    System.out.println("Inserisci eventuali richieste speciali:");
                    String specialRequests = scanner.nextLine();
                    System.out.println("Inserisci il tuo ID cliente:");
                    String clientId = scanner.nextLine();
                    Reservation newReservation = new Reservation(null, dateTime, numberOfPersons, specialRequests, clientId);
                    try {
                        customerController.newReservation(newReservation);
                        System.out.println("Prenotazione effettuata con successo!");
                    } catch (Exception e) {
                        System.out.println("Errore durante la prenotazione: " + e.getMessage());
                    }
                    break;
                case 6:
                    // Cancella una prenotazione
                    // Implementa la logica per cancellare una prenotazione
                    break;
                case 7:
                    // Torna indietro
                    return;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    public static void brigadeMenu(Scanner scanner, BrigadeController brigadeController) {
        while (true) {
            System.out.println("Menu brigata di cucina:");
            System.out.println("1. Visualizza la lista degli ordini");
            System.out.println("2. Segna un ordine come completato");
            System.out.println("3. Torna indietro");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consuma il carattere newline dopo nextInt()

            switch (choice) {
                case 1:
                    // Visualizza la lista degli ordini
                    try {
                        brigadeController.viewOrderList();
                    } catch (Exception e) {
                        System.out.println("Errore durante il recupero della lista degli ordini: " + e.getMessage());
                    }
                    break;
                case 2:
                    // Segna un ordine come completato
                    // Implementa la logica per segnare un ordine come completato
                    break;
                case 3:
                    // Torna indietro
                    return;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    public static void adminMenu(Scanner scanner, AdminController adminController) {
        while (true) {
            System.out.println("Menu amministratore:");
            System.out.println("1. Aggiungi un piatto al menu");
            System.out.println("2. Rimuovi un piatto dal menu");
            System.out.println("3. Visualizza il menu");
            System.out.println("4. Visualizza tutte le prenotazioni");
            System.out.println("5. Visualizza le prenotazioni di oggi");
            System.out.println("6. Visualizza le prenotazioni di un cliente");
            System.out.println("7. Calcola il conto di una prenotazione");
            System.out.println("8. Torna indietro");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consuma il carattere newline dopo nextInt()

            switch (choice) {
                case 1:
                    // Aggiungi un piatto al menu
                    // Implementa la logica per aggiungere un piatto al menu
                    break;
                case 2:
                    // Rimuovi un piatto dal menu
                    // Implementa la logica per rimuovere un piatto dal menu
                    break;
                case 3:
                    // Visualizza il menu
                    try {
                        adminController.viewMenu();
                    } catch (Exception e) {
                        System.out.println("Errore durante il recupero del menu: " + e.getMessage());
                    }
                    break;
                case 4:
                    // Visualizza tutte le prenotazioni
                    // Implementa la logica per visualizzare tutte le prenotazioni
                    break;
                case 5:
                    // Visualizza le prenotazioni di oggi
                    System.out.println("Inserisci la data odierna (formato: yyyy-MM-dd):");
                    String dateStr = scanner.nextLine();
                    LocalDateTime today = LocalDateTime.parse(dateStr + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    try {
                        adminController.viewDailyReservation(today);
                    } catch (Exception e) {
                        System.out.println("Errore durante il recupero delle prenotazioni di oggi: " + e.getMessage());
                    }
                    break;
                case 6:
                    // Visualizza le prenotazioni di un cliente
                    // Implementa la logica per visualizzare le prenotazioni di un cliente
                    break;
                case 7:
                    // Calcola il conto di una prenotazione
                    // Implementa la logica per calcolare il conto di una prenotazione
                    break;
                case 8:
                    // Torna indietro
                    return;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }
}
