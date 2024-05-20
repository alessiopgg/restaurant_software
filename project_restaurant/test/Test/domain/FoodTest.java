package Test.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain_model.Food;

public class FoodTest {
	 private Food food;

	    @Before
	    public void setUp() {
	        food = new Food(1, "Pizza", 8.99, "Delicious cheese pizza");
	    }

	    @Test
	    public void getIdTest() {
	        assertEquals(1, (int) food.getId());
	    }

	    @Test
	    public void setIdTest() {
	        food.setId(2);
	        assertEquals(2, (int) food.getId());
	    }

	    @Test
	    public void getNameTest() {
	        assertEquals("Pizza", food.getName());
	    }

	    @Test
	    public void setNameTest() {
	        food.setName("Burger");
	        assertEquals("Burger", food.getName());
	    }

	    @Test
	    public void getDescriptionTest() {
	        assertEquals("Delicious cheese pizza", food.getDescription());
	    }

	    @Test
	    public void setDescriptionTest() {
	        food.setDescription("Tasty beef burger");
	        assertEquals("Tasty beef burger", food.getDescription());
	    }

	    @Test
	    public void getCostTest() {
	        assertEquals(8.99, food.getCost(), 0.001);
	    }

	    @Test
	    public void setCostTest() {
	        food.setCost(5.99);
	        assertEquals(5.99, food.getCost(), 0.001);
	    }

	 
	    @Test
	    public void toStringTest() {
	        String expected = "Food[id=1, name=Pizza, cost=8.99, description=Delicious cheese pizza]";
	        assertEquals(expected, food.toString());
	    }
}
