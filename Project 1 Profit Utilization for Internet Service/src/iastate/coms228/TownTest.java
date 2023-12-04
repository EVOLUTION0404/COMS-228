package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TownTest {
	@Test
	void test() {
		Town t = new Town(3, 4);
		assertEquals(t.getLength(), 3);
		assertEquals(t.getWidth(), 4);
		
		t.randomInit(10);
		
		System.out.println(t);
	}
	
	
	}

