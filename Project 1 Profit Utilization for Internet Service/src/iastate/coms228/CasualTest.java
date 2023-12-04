package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CasualTest{

	@Test
	void test() {
		Town t = new Town(2, 2);
		t.grid[0][0] = new Casual(t, 0, 0);
		t.grid[0][1] = new Streamer(t, 0, 1);
		t.grid[1][0] = new Casual(t, 1, 0);
		t.grid[1][1] = new Streamer(t, 1, 1);
		assertEquals(State.RESELLER, t.grid[0][0].next(t).who());
	}
	
	@Test
	void test2() {
		Town t = new Town(2, 2);
		t.grid[0][0] = new Casual(t, 0, 0);
		t.grid[0][1] = new Reseller(t, 0, 1);
		t.grid[1][0] = new Empty(t, 1, 0);
		t.grid[1][1] = new Empty(t, 1, 1);
		assertEquals(State.OUTAGE, t.grid[0][0].next(t).who());
	}
	
	@Test
	void test3() {
		Town t = new Town(3, 3);
		t.grid[0][0] = new Outage(t, 0, 0);
		t.grid[0][1] = new Outage(t, 0, 1);
		t.grid[0][2] = new Outage(t, 0, 2);
		t.grid[1][0] = new Casual(t, 1, 0);
		t.grid[1][1] = new Casual(t, 1, 1);
		t.grid[1][2] = new Casual(t, 1, 2);
		t.grid[2][0] = new Casual(t, 2, 0);
		t.grid[2][1] = new Casual(t, 2, 1);
		t.grid[2][2] = new Casual(t, 2, 2);
		assertEquals(State.STREAMER, t.grid[1][1].next(t).who());
	}

	@Test
	void test4() {
		Town t = new Town(2, 2);
		t.grid[0][0] = new Casual(t, 0, 0);
		t.grid[0][1] = new Streamer(t, 0, 1);
		t.grid[1][0] = new Empty(t, 1, 0);
		t.grid[1][1] = new Empty(t, 1, 1);
		assertEquals(State.STREAMER, t.grid[0][0].next(t).who());
	}
	
	@Test
	void test5() {
		Town t = new Town(2, 2);
		t.grid[0][0] = new Casual(t, 0, 0);
		t.grid[0][1] = new Empty(t, 0, 1);
		t.grid[1][0] = new Empty(t, 1, 0);
		t.grid[1][1] = new Empty(t, 1, 1);
		assertEquals(State.CASUAL, t.grid[0][0].next(t).who());
	}
}