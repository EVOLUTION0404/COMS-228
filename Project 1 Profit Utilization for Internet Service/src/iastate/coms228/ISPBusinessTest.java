package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ISPBusinessTest {
	
	@Test
	void test() {
		Town t = new Town(2, 2);
		t.grid[0][0] = new Casual(t, 0, 0);
		t.grid[0][1] = new Streamer(t, 0, 1);
		t.grid[1][0] = new Empty(t, 1, 0);
		t.grid[1][1] = new Outage(t, 1, 1);
		Town nTown = ISPBusiness.updatePlain(t);
		
		assertEquals(State.STREAMER, nTown.grid[0][0].who());
		assertEquals(State.EMPTY, nTown.grid[0][1].who());
		assertEquals(State.RESELLER, nTown.grid[1][0].who());
		assertEquals(State.EMPTY, nTown.grid[1][1].who());
	}
	
	@Test
	void test2() {
		Town t = new Town(2, 2);
		
		for(int r = 0;r < t.getLength();r++) {
			for(int c = 0; c < t.getWidth(); c++) {
				t.grid[r][c] = new Casual (t, r, c);
			}
		}
	}

}
