package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OutageTest {
	
@Test
void test() {
	Town t = new Town(2, 2);
	t.grid[0][0] = new Outage(t, 0, 0);
	t.grid[0][1] = new Streamer(t, 0, 1);
	t.grid[1][0] = new Outage(t, 1, 0);
	t.grid[1][1] = new Streamer(t, 1, 1);
	assertEquals(State.EMPTY, t.grid[0][0].next(t).who());
}

}
