package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TownCellTest {
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	@Test
	void test() {
		int nums[] = new int[5];
		Town t = new Town(3, 3);
		t.grid[0][0] = new Casual(t, 0, 0);
		t.grid[0][1] = new Outage(t, 0, 1);
		t.grid[0][2] = new Empty(t, 0, 2);
		t.grid[1][0] = new Streamer(t, 1, 0);
		t.grid[1][1] = new Casual(t, 1, 1);
		t.grid[1][2] = new Reseller(t, 1, 2);
		t.grid[2][0] = new Casual(t, 2, 0);
		t.grid[2][1] = new Casual(t, 2, 1);
		t.grid[2][2] = new Empty(t, 2, 2);
		
		t.grid[1][1].census(nums);
		
		assertEquals(nums[RESELLER],1);
		assertEquals(nums[EMPTY],2);
		assertEquals(nums[CASUAL],3);
		assertEquals(nums[OUTAGE],1);
		assertEquals(nums[STREAMER],1);
	}

}
