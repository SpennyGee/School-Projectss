package Homework01;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

// I'm just using this class to test things with github

// adding a comment here for sourcetree test
// comment two

// comment four

// comment five

// comment six

public class Homework1Test {

	@Test
	public void rowRemovalTest() {
		
		Grid g = new Grid();
		
		for(int row = 0; row < Grid.HEIGHT; row ++) {
			for(int col = 0; col < Grid.WIDTH; col ++) {
				if(row != 9 || col==5 || col == 6)
					g.set(row, col, Color.BLUE);
				
			}
		}
		
		g.checkRows();
		
		for(int row = 0; row < Grid.HEIGHT; row ++) {
			for(int col = 0; col < Grid.WIDTH; col ++) {
				if(row == (g.HEIGHT - 1) && (col == 5 || col == 6)) {
					assertTrue(g.isSet(row, col));
//					assertFalse(!g.isSet(row, col));
				} else {
					assertFalse(g.isSet(row, col));
//					assertTrue(!g.isSet(row, col));
				}
			}
		}
	}
	
//	@Test
//	public void canMoveTest() {
//		
//		Grid g = new Grid();
//		
//		Square s = new Square(g, g.HEIGHT - 2, 0, Color.BLUE, true);
//		
//		assertFalse(s.canMove(Direction.LEFT));
//		assertTrue(s.canMove(Direction.DOWN));
//		assertTrue(s.canMove(Direction.RIGHT));
//		
//		s = new Square(g, g.HEIGHT/2, g.WIDTH/2, Color.BLUE, true);
//		
//		assertTrue(s.canMove(Direction.LEFT));
//		assertTrue(s.canMove(Direction.DOWN));
//		assertTrue(s.canMove(Direction.RIGHT));		
//		
//		s = new Square(g, g.HEIGHT -1, g.WIDTH-1, Color.BLUE, true);
//		
//		assertTrue(s.canMove(Direction.LEFT));
//		assertFalse(s.canMove(Direction.DOWN));
//		assertFalse(s.canMove(Direction.RIGHT));	
//		
//		
//	}
//	
	public void canMoveTestLShape() {
		
	}
	
	
}
