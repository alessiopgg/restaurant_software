package Test.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain_model.Customer;

public class CustomerTest {
	private Customer customer;

    @Before
    public void setUp() {
        customer = new Customer(1, "Rossi", "Mario", "1234567890");
    }

    @Test
    public void getIdTest() {
        assertEquals(1,(int) customer.getId());
    }

    @Test
    public void setIdTest() {
        customer.setId(2);
        assertEquals(2,(int) customer.getId());
    }

    @Test
    public void getSurnametest() {
        assertEquals("Rossi", customer.getSurname());
    }

    @Test
    public void setSurnameTest() {
        customer.setSurname("Bianchi");
        assertEquals("Bianchi", customer.getSurname());
    }

    @Test
    public void getNameTest() {
        assertEquals("Mario", customer.getName());
    }

    @Test
    public void testSetName() {
        customer.setName("Luigi");
        assertEquals("Luigi", customer.getName());
    }

    @Test
    public void getPhoneTest() {
        assertEquals("1234567890", customer.getPhone());
    }

    @Test
    public void setPhoneTest() {
        customer.setPhone("0987654321");
        assertEquals("0987654321", customer.getPhone());
    }
}
