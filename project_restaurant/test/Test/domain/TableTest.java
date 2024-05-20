package Test.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain_model.Table;

public class TableTest {
    private Table table;

    @Before
    public void setUp() {
        table = new Table(1, 10, 4);
    }

    @Test
    public void getIdTest() {
        assertEquals(1, (int) table.getId());
    }

    @Test
    public void setIdTest() {
        table.setId(2);
        assertEquals(2, (int) table.getId());
    }

    @Test
    public void getNumberTest() {
        assertEquals(10, (int) table.getNumber());
    }

    @Test
    public void setNumberTest() {
        table.setNumber(20);
        assertEquals(20, (int) table.getNumber());
    }

    @Test
    public void getSeatNumbersTest() {
        assertEquals(4, (int) table.getSeatNumbers());
    }

    @Test
    public void setSeatNumbersTest() {
        table.setSeatNumbers(6);
        assertEquals(6, (int) table.getSeatNumbers());
    }
}
