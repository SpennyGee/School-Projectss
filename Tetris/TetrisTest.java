package Homework02;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

public class TetrisTest {

	/**
	 * This test checks that the checkRows() method (that clears filled rows) works
	 */
	@Test
	public void testCheckRows() {
		
		// creates a new grid
		Grid grid = new Grid();
		
		// iterates through the squares in the grid and turns them blue
		// except for row nine for the squares not in the 5th or 6th column
		for(int row = 0; row < Grid.HEIGHT; row ++) {
			for(int col = 0; col < Grid.WIDTH; col ++) {
			
				// fills in the squares at given row and column blue when statement is true
				if(row != 9 || col == 5 || col == 6) {
					grid.set(row,  col, Color.BLUE);	
				}
			}
		}
		
		// runs the checkRows() method on the grid
		// there should only be two blue squares on the bottom row in columns 5 and 6
		grid.checkRows();
		
		// iterates through the squares in the grid
		for(int row = 0; row < Grid.HEIGHT; row ++) {
			for(int col = 0; col < Grid.WIDTH; col ++) {
				
				// checks that all of the squares are empty
				// except for the two on the bottom row in columns 5 and 6
				if(row == (Grid.HEIGHT - 1) && (col == 5 || col == 6)) {
					assertTrue(grid.isSet(row, col));
				} else {
					assertFalse(grid.isSet(row, col));
				}
			}
		}
	}
	
	// square tests
	
	/** tests that a square at the left edge can't move left */
	@Test
	public void testSquareLeftEdge() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new filled square on the left edge one row above the bottom
		Square square = new Square(grid, Grid.HEIGHT - 2, 0, Color.BLUE, true); 
		
		// the square should not be able to move left
		assertFalse(square.canMove(Direction.LEFT));
		
		// the square should be able to move right and down
		assertTrue(square.canMove(Direction.RIGHT));
		assertTrue(square.canMove(Direction.DOWN));
	}
	
	/** tests that a square with nothing around it can move in any direction */
	@Test
	public void testSquareMoveAnywhere() {
	
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new filled square roughly in the middle of the grid
		Square square = new Square(grid, Grid.HEIGHT/2, Grid.WIDTH/2, Color.BLUE, true);
		
		// the square should be able to move left, right, and down
		assertTrue(square.canMove(Direction.LEFT));
		assertTrue(square.canMove(Direction.RIGHT));
		assertTrue(square.canMove(Direction.DOWN));
	}
	
	/** tests that a square at the right edge can't move right */
	@Test
	public void testSquareRightEdge() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new filled square on the right edge one row above the bottom
		Square square = new Square(grid, Grid.HEIGHT - 2, Grid.WIDTH - 1, Color.BLUE, true);
		
		// the square should not be able to move right
		assertFalse(square.canMove(Direction.RIGHT));

		// the square should be able to move left and down
		assertTrue(square.canMove(Direction.LEFT));
		assertTrue(square.canMove(Direction.DOWN));
	}
	
	/** test that a square surrounded by other squares cannot move in any direction */
	@Test
	public void testSquareSurrounded() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// iterates through all of the squares in the grid and turns them blue
		// except at column 5 in the 2nd to bottom row
		for(int row = 0; row < Grid.HEIGHT; row++) {
			for(int col = 0; col < Grid.WIDTH; col++) {
				if(!(row == Grid.HEIGHT - 2 && col == 5)) {
					grid.set(row,  col, Color.BLUE);		
				}
			}
		}

		// creates a new filled square at column 5 in the 2nd to bottom row
		Square square = new Square(grid, Grid.HEIGHT - 2, 5, Color.BLUE, true);
		
		// the square should not be able to move left, right, or down
		assertFalse(square.canMove(Direction.LEFT));
		assertFalse(square.canMove(Direction.RIGHT));
		assertFalse(square.canMove(Direction.DOWN));
	}
		
	// LShape tests
	
	/** tests that an LShape piece in the bottom left corner cannot move */
	@Test
	public void testLShapeBottomLeftCorner() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new LShape piece in the bottom left corner
		LShape lShape = new LShape(Grid.HEIGHT -2, 0, grid);
		
		// the LShape piece should not be able to move left, right, down, or drop
		assertFalse(lShape.canMove(Direction.LEFT));
		assertFalse(lShape.canMove(Direction.RIGHT));
		assertFalse(lShape.canMove(Direction.DOWN));
		assertFalse(lShape.canMove(Direction.DROP));
	}
	
	
	/** test that an LShape with nothing around it can move in any direction */
	@Test
	public void testLShapeMoveAnywhere() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new LShape piece with nothing around it
		LShape lShape = new LShape(Grid.HEIGHT -3, 1, grid);
		
		// the LShape should be able to move left, right, down, or drop
		assertTrue(lShape.canMove(Direction.LEFT));
		assertTrue(lShape.canMove(Direction.RIGHT));
		assertTrue(lShape.canMove(Direction.DOWN));
		assertTrue(lShape.canMove(Direction.DROP));
	}
	
	/** tests that a LShape cannot move through existing squares to the left */
	@Test
	public void testLShapeSquaresLeft() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// fills 4 squares in bottom left corner to create an artificial LShape
		for(int i = 0; i < 3; i ++) {
			grid.set(Grid.HEIGHT - (1 + i), 0, Color.BLUE);
		}
		grid.set(Grid.HEIGHT - 1, 1, Color.BLUE);
		
		// creates a new LShape piece who's left side borders the filled squares
		LShape lShape = new LShape(Grid.HEIGHT - 4, 1, grid);

		// the LShape should not be able to move left
		assertFalse(lShape.canMove(Direction.LEFT));
		
		// the LShape should be able to move right, down, or drop
		assertTrue(lShape.canMove(Direction.RIGHT));
		assertTrue(lShape.canMove(Direction.DOWN));
		assertTrue(lShape.canMove(Direction.DROP));
	}
	
	/** tests that the LShape cannot move through existing squares below */
	@Test
	public void testLShapeSquaresBelow() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// fills 4 squares in bottom left corner to create an artificial LShape
		for(int i = 0; i < 3; i ++) {
			grid.set(Grid.HEIGHT - (1 + i), 0, Color.BLUE);
		}
		grid.set(Grid.HEIGHT - 1, 1, Color.BLUE);
		
		// creates a new LShape piece the square above one of the artificial squares
		// and to the right of two others
		LShape lShape = new LShape(Grid.HEIGHT -3, 1, grid);
		
		// the LShape piece should not be able to move left, right, down, or drop
		assertFalse(lShape.canMove(Direction.LEFT));
		assertFalse(lShape.canMove(Direction.RIGHT));
		assertFalse(lShape.canMove(Direction.DOWN));
		assertFalse(lShape.canMove(Direction.DROP));
	}
	
	/** tests that a LShape piece can only move across the board 8 times horizontally from the far edge */
	@Test
	public void testLShapeMoveHorizontal() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new LShape piece at the left edge of the grid
		LShape lShape = new LShape(Grid.HEIGHT/2, 0, grid);
		  
		// int used to count how many times the LShape piece moved
		int count = 0;
		  
		// moves the LShape piece to the right until it cannot move to the right
		// each times it moves count increase by 1
		while(lShape.canMove(Direction.RIGHT)) {
			lShape.move(Direction.RIGHT);
			count ++;
		}

		// the LShape piece should not be able to move anymore
		assertFalse(lShape.canMove(Direction.RIGHT));
		  
		// the LShape piece should have moved 8 times
		assertTrue(count == 8); 
	}
	
	/** tests that the LShape piece cannot move after it drops */
	@Test
	public void testLShapeDropMovement() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		  
		// creates a new LShape piece high up on the grid
		LShape lShape = new LShape(1, 1, grid); 
		  
		// drops the piece down to the bottom
		while(lShape.canMove(Direction.DROP)) {
			lShape.move(Direction.DROP);  
		}
		  
		// after dropping the LShape piece should not be able to move left, right, down, or drop
		assertFalse(lShape.canMove(Direction.LEFT));
		assertFalse(lShape.canMove(Direction.RIGHT));
		assertFalse(lShape.canMove(Direction.DOWN));
		assertFalse(lShape.canMove(Direction.DROP));
	}
	
	
	
	/** Beyond here are the new unit tests for Homework02 */
	
	
	// can rotate tests
	
	/** tests that a piece can rotate with nothing around it */
	@Test
	public void testRotateAnywhere() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new Bar piece roughly in the middle of the grid
		Bar bar = new Bar(Grid.HEIGHT/2, Grid.WIDTH/2, grid);
		
		// the piece should be able to rotate
		assertTrue(bar.canRotate());
	}
	
	/** tests that the piece can rotate when adjacent to grid border when it doesn't pass through it*/
	@Test
	public void testAgainstBorder() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new MirrorZShape piece on the right edge of the grid
		MirrorZShape mirrorZShape = new MirrorZShape(Grid.HEIGHT/2, Grid.WIDTH - 2, grid);
				
		// the piece should be able to rotate
		assertTrue(mirrorZShape.canRotate());
	}
	
	/** tests that a piece can rotate when the grid is filled in just outside of its rotation path on all sides */
	@Test
	public void test() {

		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new TShape piece roughly in the middle of the grid
		TShape tShape = new TShape(Grid.HEIGHT/2, Grid.WIDTH/2, grid);
		
		// fills in the grid in a loop that goes all the way around the TShape's rotation path
		for(int i = 0; i < 4; i ++) {
			grid.set(Grid.HEIGHT/2 - 3, Grid.WIDTH/2 - 2 + i, Color.BLUE);
			grid.set(Grid.HEIGHT/2 + 1, Grid.WIDTH/2 - 1 + i, Color.BLUE);
			grid.set(Grid.HEIGHT/2 - 2 + i, Grid.WIDTH/2 - 2, Color.BLUE);
			grid.set(Grid.HEIGHT/2 - 3 + i, Grid.WIDTH/2 + 2, Color.BLUE);
		}
				
		// the piece should be able to rotate
		assertTrue(tShape.canRotate());	
	}
	
	
	
	
	// cannot rotate tests
	
	/** tests that a piece cannot rotate when right border blocks rotation */
	@Test
	public void testRightBorder() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new MirrorLShape piece at the right edge of the grid
		MirrorLShape mirrorLShape = new MirrorLShape(8, Grid.WIDTH -1, grid);
		
		// to rotate the piece would have to cross over the right edge of the grid
		// so the piece should not be able to rotate
		assertFalse(mirrorLShape.canRotate());
	}
	
	/** tests that a piece cannot rotate when left border blocks rotations */
	@Test
	public void testLeftBorder() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new MirrorLShape piece at the left edge of the grid
		LShape lShape = new LShape(8, 0, grid);
		
		// to rotate the piece would have to cross over the left edge of the grid
		// so the piece should not be able to rotate
		assertFalse(lShape.canRotate());
	}
	
	/** tests that a piece cannot rotate when top border blocks rotation */
	@Test
	public void testTopBorder() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new MirrorLShape piece at the top of the grid
		ZShape zShape = new ZShape(1, 5, grid);
		
		// to rotate the piece would have to cross over the top edge of the grid
		// so the piece should not be able to rotate
		assertFalse(zShape.canRotate());
	}
	
	/** tests that a piece cannot rotate when bottom border blocks rotation */
	@Test
	public void testBottomBorder() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new Bar piece one row above the bottom row of the grid
		Bar bar = new Bar(Grid.HEIGHT - 1, 5, grid);
		
		// to rotate the piece would have to cross over the bottom edge of the grid
		// so the piece should not be able to rotate
		assertFalse(bar.canRotate());
	}
	
	/** tests than a piece cannot rotate when grid is set in rotation path (above) */
	@Test
	public void testGridSetAbove() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new TShape piece roughly in the middle of the grid
		TShape tShape = new TShape(Grid.HEIGHT/2, Grid.WIDTH/2, grid);
		
		// fills 3 squares above the TShape piece
		for(int i = 0; i < 3; i ++) {
			grid.set(Grid.HEIGHT/2 - 2, Grid.WIDTH/2 - 1 + i, Color.BLUE);
		}
		
		// to rotate the piece would have to cross over the set grid above
		// so the piece should not be able to rotate
		assertFalse(tShape.canRotate());		
	}
	
	/** tests that a piece cannot rotate when grid is set in rotation path (below) */
	@Test
	public void testGridSetBelow() {
	
		// creates a new blank grid
		Grid grid = new Grid();
		
		// creates a new Bar piece roughly in the middle of the grid
		Bar bar = new Bar(Grid.HEIGHT/2, Grid.WIDTH/2, grid);
		
		// fills a square in below the square of the  Bar piece
		grid.set(Grid.HEIGHT/2 + 1, Grid.WIDTH/2 + 2, Color.BLUE);
		
		// to rotate the piece would have to cross over the set grid below
		// so the piece should not be able to rotate
		assertFalse(bar.canRotate());	
	}
	
	
	
	
	
	// can rotate and getting square arrays for each piece
	
	// sorted in alphabetical order from piece name
	// Bar, LShape, MirrorLShape, MirrorZShape, TShape, ZShape
	// Block should not be able to rotate so it's at the end
	
	/** tests that a Bar is at it's expected location after rotating */
	@Test
	public void testBarRotation() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// the row and column at the center of the piece
		int row = Grid.HEIGHT/2;
		int col = Grid.WIDTH/2;
		
		// creates a new Bar piece roughly in the middle of the grid
		Bar bar = new Bar(row, col, grid);
		
		// rotates the piece
		bar.rotate();
		
		// an array of the expected locations for the piece's rows and columns after the rotation
		int[][] expectedArray = {{row - 2, col}, {row - 1, col}, {row, col}, {row + 1, col}};

		// gets the location of the Bar squares after the rotation
		Square[] squareArray = bar.getSquareArray();
		
		// makes sure that the current squares are where they're supposed to be
		for(int i = 0; i < squareArray.length; i ++) {
			assertTrue(squareArray[i].getRow() == expectedArray[i][0]);
			assertTrue(squareArray[i].getCol() == expectedArray[i][1]);
		}
	}
	
	/** tests that a LShape is at it's expected location after rotating */
	@Test
	public void testLShapeRotation() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// the row and column at the center of the piece
		int row = Grid.HEIGHT/2;
		int col = Grid.WIDTH/2;
		
		// creates a new LShape piece roughly in the middle of the grid
		LShape lShape = new LShape(row, col, grid);
		
		// rotates the piece
		lShape.rotate();
		
		// an array of the expected locations for the piece's rows and columns after the rotation
		int[][] expectedArray = {{row, col + 1}, {row, col}, {row, col - 1}, {row + 1, col - 1}};

		// gets the location of the LShape squares after the rotation
		Square[] squareArray = lShape.getSquareArray();
		
		// makes sure that the current squares are where they're supposed to be
		for(int i = 0; i < squareArray.length; i ++) {
			assertTrue(squareArray[i].getRow() == expectedArray[i][0]);
			assertTrue(squareArray[i].getCol() == expectedArray[i][1]);
		}
	}
	
	/** tests that a MirrorLShape is at it's expected location after rotating */
	@Test
	public void testMirrorLShapeRotation() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// the row and column at the center of the piece
		int row = Grid.HEIGHT/2;
		int col = Grid.WIDTH/2;
		
		// creates a new MirrorLShape piece roughly in the middle of the grid
		MirrorLShape mirrorLShape = new MirrorLShape(row, col, grid);
		
		// rotates the piece
		mirrorLShape.rotate();
		
		// an array of the expected locations for the piece's rows and columns after the rotation
		int[][] expectedArray = {{row, col + 1}, {row, col}, {row, col - 1}, {row - 1, col - 1}};

		// gets the location of the MirrorLShape squares after the rotation
		Square[] squareArray = mirrorLShape.getSquareArray();
		
		// makes sure that the current squares are where they're supposed to be
		for(int i = 0; i < squareArray.length; i ++) {
			assertTrue(squareArray[i].getRow() == expectedArray[i][0]);
			assertTrue(squareArray[i].getCol() == expectedArray[i][1]);
		}
	}
	
	/** tests that a MirrorZShape is at it's expected location after rotating */
	@Test
	public void testMirrorZShapeRotation() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// the row and column at the center of the piece
		int row = Grid.HEIGHT/2;
		int col = Grid.WIDTH/2;
		
		// creates a new MirrorZShape piece roughly in the middle of the grid
		MirrorZShape mirrorZShape = new MirrorZShape(row, col, grid);
		
		// rotates the piece
		mirrorZShape.rotate();
		
		// an array of the expected locations for the piece's rows and columns after the rotation
		int[][] expectedArray = {{row - 2, col}, {row - 1, col}, {row - 1, col - 1}, {row, col - 1}};

		// gets the location of the MirrorZShape's squares after the rotation
		Square[] squareArray = mirrorZShape.getSquareArray();
		
		// makes sure that the current squares are where they're supposed to be
		for(int i = 0; i < squareArray.length; i ++) {
			assertTrue(squareArray[i].getRow() == expectedArray[i][0]);
			assertTrue(squareArray[i].getCol() == expectedArray[i][1]);
		}
	}
	
	/** tests that a TShape is at it's expected location after rotating */
	@Test
	public void testTShapeRotation() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// the row and column at the center of the piece
		int row = Grid.HEIGHT/2;
		int col = Grid.WIDTH/2;
		
		// creates a new TShape piece roughly in the middle of the grid
		TShape tShape = new TShape(row, col, grid);
		
		// rotates the piece
		tShape.rotate();
		
		// an array of the expected locations for the piece's rows and columns after the rotation
		int[][] expectedArray = {{row - 2, col}, {row - 1, col}, {row, col}, {row - 1, col - 1}};

		// gets the location of the TShape's squares after the rotation
		Square[] squareArray = tShape.getSquareArray();
		
		// makes sure that the current squares are where they're supposed to be
		for(int i = 0; i < squareArray.length; i ++) {
			assertTrue(squareArray[i].getRow() == expectedArray[i][0]);
			assertTrue(squareArray[i].getCol() == expectedArray[i][1]);
		}
	}
	
	/** tests that a ZShape is at it's expected location after rotating */
	@Test
	public void testZShapeRotation() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// the row and column at the center of the piece
		int row = Grid.HEIGHT/2;
		int col = Grid.WIDTH/2;
		
		// creates a new ZShape piece roughly in the middle of the grid
		ZShape zShape = new ZShape(row, col, grid);
		
		// rotates the piece
		zShape.rotate();
		
		// an array of the expected locations for the piece's rows and columns after the rotation
		int[][] expectedArray = {{row, col}, {row - 1, col}, {row - 1, col - 1}, {row - 2, col - 1}};

		// gets the location of the ZShape's squares after the rotation
		Square[] squareArray = zShape.getSquareArray();
		
		// makes sure that the current squares are where they're supposed to be
		for(int i = 0; i < squareArray.length; i ++) {
			assertTrue(squareArray[i].getRow() == expectedArray[i][0]);
			assertTrue(squareArray[i].getCol() == expectedArray[i][1]);
		}
	}
	
	
	
	
	// cannot rotate and getting square arrays for the piece

	/** tests that a Block cannot rotate */
	@Test
	public void testBlockRotation() {
		
		// creates a new blank grid
		Grid grid = new Grid();
		
		// the row and column at the center of the piece
		int row = Grid.HEIGHT/2;
		int col = Grid.WIDTH/2;
		
		// creates a new Block piece roughly in the middle of the grid
		Block bar = new Block(row, col, grid);
		
		// tries to rotates the piece
		bar.rotate();
		
		// an array of the expected locations for the piece's rows and columns after the rotation method
		// it should not have moved
		int[][] expectedArray = {{row - 1, col - 1}, {row - 1, col}, {row, col - 1}, {row, col}};

		// gets the location of the Block squares after the rotation method is called
		Square[] squareArray = bar.getSquareArray();
		
		// makes sure that the current squares are where they're supposed to be
		for(int i = 0; i < squareArray.length; i ++) {
			assertTrue(squareArray[i].getRow() == expectedArray[i][0]);
			assertTrue(squareArray[i].getCol() == expectedArray[i][1]);
		}
	}
}