package Test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Orm.CustomerDAO;
import domain_model.Customer;

public class CustomerDAOTest {

    private CustomerDAO customerDAO;
    private Customer testCustomer;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        customerDAO = new CustomerDAO();
        
        testCustomer = new Customer(null,"surname", "name", "1234567899");
        
        customerDAO.addCustomer(testCustomer);
        testCustomer.setId(customerDAO.getId(testCustomer.getPhone()));
    }
    
    //in fase di setUp aggiungiamo un cliente al database
  
    @Test
    public void addCustomerTest() throws ClassNotFoundException, SQLException {
    	assertEquals("surname",customerDAO.getCustomer(testCustomer.getId()).getSurname());
    	assertEquals("name",customerDAO.getCustomer(testCustomer.getId()).getName());
    	assertEquals("1234567899",customerDAO.getCustomer(testCustomer.getId()).getPhone());
    }
    
    @Test
    public void deleteCustomerTest() throws ClassNotFoundException, SQLException {
    	customerDAO.deleteCustomer(testCustomer.getId());
    	assertNull(customerDAO.getCustomer(testCustomer.getId()));
    	
    	Customer customer= new Customer(null, null, null, null);
    	assertNull(customer.getId());
    }
    
    @Test
    public void findClientTest() throws ClassNotFoundException, SQLException {
    	assertTrue(customerDAO.findClient(testCustomer));
    	
    	Customer customer= new Customer(null, null, null, null);
    	assertEquals(false,customerDAO.findClient(customer));
    }
    //TODO: FIX modifica il db trova soluzione
    @Test
    public void getAllCustomerTest() throws ClassNotFoundException, SQLException{
    	
        // Ottieni tutti i clienti dal database
        ArrayList<Customer> customerList = customerDAO.getAllCustomer();
        
        // Verifica che l'elenco restituito non sia nullo
        assertNotNull(customerList);
        
        // Verifica che l'elenco contenga i clienti inseriti
        assertTrue(customerList.size() >= 2); // Almeno due clienti devono essere presenti
        boolean customerFound = false;
        Customer customer1=new Customer(null, null, null, null);
        boolean customer1Found = false;
        for (Customer customer : customerList) {
            if (customer.getId().equals(customer.getId())){
                customerFound = true;
            }
            if (customer.getId().equals(customer1.getId())) {
                customer1Found = true;
            }
        }
        assertEquals(true,customerFound);
        assertEquals(false,customer1Found);

        
    }
    
    
    @Test
    public void checkNumberPhoneTest()throws ClassNotFoundException, SQLException {
    	assertTrue(customerDAO.checkNumberPhone(testCustomer));
    	
    	Customer customer= new Customer(null, null, null, null);
    	assertFalse(customerDAO.checkNumberPhone(customer));
    }
    
    @Test
    public void getIdTest()throws ClassNotFoundException, SQLException  {
    	assertEquals(testCustomer.getId(), customerDAO.getId(testCustomer.getPhone()));
    	Customer customer= new Customer(null, null, null, null);
    	assertNull(customerDAO.getId(customer.getPhone()));
    }
    
    @Test
    public void getCustomerTest()throws ClassNotFoundException, SQLException{
    	assertEquals(testCustomer.getId(), customerDAO.getCustomer(testCustomer.getId()).getId());
    	assertEquals(testCustomer.getName(), customerDAO.getCustomer(testCustomer.getId()).getName());
    	assertEquals(testCustomer.getPhone(), customerDAO.getCustomer(testCustomer.getId()).getPhone());
    	assertEquals(testCustomer.getSurname(), customerDAO.getCustomer(testCustomer.getId()).getSurname());

    }

    @After
    public void tearDown() {
        try {
            // Eliminazione del cliente dal database
            customerDAO.deleteCustomer(testCustomer.getId());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
