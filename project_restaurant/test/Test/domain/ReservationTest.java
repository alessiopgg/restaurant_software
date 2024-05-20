package Test.domain;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import domain_model.Reservation;

public class ReservationTest {
	 private Reservation reservation;

	    @Before
	    public void setUp() {
	        reservation = new Reservation(1, LocalDateTime.of(2024, 5, 19, 12, 30), 4, "Near window", 5, 101);
	    }

	    @Test
	    public void getIdTest() {
	        assertEquals(1, (int) reservation.getId());
	    }

	    @Test
	    public void setIdTest() {
	        reservation.setId(2);
	        assertEquals(2, (int) reservation.getId());
	    }

	    @Test
	    public void getDateTest() {
	        LocalDateTime expectedDate = LocalDateTime.of(2024, 5, 19, 12, 30);
	        assertEquals(expectedDate, reservation.getDate());
	    }

	    @Test
	    public void setDateTest() {
	        LocalDateTime newDate = LocalDateTime.of(2024, 6, 20, 18, 0);
	        reservation.setDate(newDate);
	        assertEquals(newDate, reservation.getDate());
	    }

	    @Test
	    public void getNumberOfPersonTest() {
	        assertEquals(4, (int) reservation.getNumberOfPerson());
	    }

	    @Test
	    public void setNumberOfPersonTest() {
	        reservation.setNumberOfPerson(5);
	        assertEquals(5, (int) reservation.getNumberOfPerson());
	    }

	    @Test
	    public void getSpecialRequestTest() {
	        assertEquals("Near window", reservation.getSpecialRequest());
	    }

	    @Test
	    public void setSpecialRequestTest() {
	        reservation.setSpecialRequest("Away from kitchen");
	        assertEquals("Away from kitchen", reservation.getSpecialRequest());
	    }

	    @Test
	    public void getTableTest() {
	        assertEquals(5, (int) reservation.getTable());
	    }

	    @Test
	    public void setTableTest() {
	        reservation.setTable(10);
	        assertEquals(10, (int) reservation.getTable());
	    }

	    @Test
	    public void getNameTest() {
	        assertEquals(101, (int) reservation.getName());
	    }

	    @Test
	    public void setNameTest() {
	        reservation.setName(102);
	        assertEquals(102, (int) reservation.getName());
	    }

	    @Test
	    public void getBillTest() {
	        assertEquals(0, (int) reservation.getBill());
	    }

	    @Test
	    public void setBillTest() {
	        reservation.setBill(200);
	        assertEquals(200, (int) reservation.getBill());
	    }
}
