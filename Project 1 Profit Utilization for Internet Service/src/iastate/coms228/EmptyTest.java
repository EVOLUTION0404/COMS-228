package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EmptyTest {

	@Test
	void test() 
	{
		Town t = new Town(2, 2);
		t.grid[0][0] = new Empty(t, 0, 0);
		t.grid[0][1] = new Streamer(t, 0, 1);
		t.grid[1][0] = new Casual(t, 1, 0);
		t.grid[1][1] = new Streamer(t, 1, 1);
		assertEquals(State.RESELLER, t.grid[0][0].next(t).who());
	}
	
	@Test
	void test2() 
	{
		Town t = new Town(2, 2);
		t.grid[0][0] = new Empty(t, 0, 0);
		t.grid[0][1] = new Outage(t, 0, 1);
		t.grid[1][0] = new Casual(t, 1, 0);
		t.grid[1][1] = new Empty(t, 1, 1);
		assertEquals(State.CASUAL, t.grid[0][0].next(t).who());
	}
	

}
