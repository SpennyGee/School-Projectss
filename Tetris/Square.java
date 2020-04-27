package Homework02;
import java.awt.Color;
import java.awt.Graphics;

/**
 * One Square on our Tetris Grid or one square in our Tetris game piece
 * 
 * @author CSC 143
 */
public class Square {

	private Grid grid; // the environment where this Square is

	private int row, col; // the grid location of this Square

	private boolean ableToMove; // true if this Square can move

	private Color color; // the color of this Square
	
	
	// possible move directions are defined by the Game class

	// dimensions of a Square
	public static final int WIDTH = 20;

	public static final int HEIGHT = 20;

	/**
	 * Creates a square
	 * 
	 * @param g
	 *            the Grid for this Square
	 * @param row
	 *            the row of this Square in the Grid
	 * @param col
	 *            the column of this Square in the Grid
	 * @param c
	 *            the Color of this Square
	 * @param mobile
	 *            true if this Square can move
	 * 
	 * @throws IllegalArgumentException
	 *             if row and col not within the Grid
	 */
	public Square(Grid g, int row, int col, Color c, boolean mobile) {
		if (row < 0 || row > Grid.HEIGHT - 1)
			throw new IllegalArgumentException("Invalid row =" + row);
		if (col < 0 || col > Grid.WIDTH - 1)
			throw new IllegalArgumentException("Invalid column  = " + col);

		// initialize instance variables
		grid = g;
		this.row = row;
		this.col = col;
		color = c;
		ableToMove = mobile;
	}

	/**
	 * Returns the row for this Square
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column for this Square
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Returns true if this Square can move 1 spot in direction d
	 * 
	 * @param direction
	 *            the direction to test for possible move
	 */
	public boolean canMove(Direction direction) {
		
		if (!ableToMove)
			return false;

		boolean move = true;
		
		// if the given direction is blocked, we can't move
		// remember to check the edges of the grid
		switch (direction) {
		
		// Both DROP and DOWN need to check for space
		// in the row below below the current row
		case DOWN:
		case DROP:
			if (row == (Grid.HEIGHT - 1) || grid.isSet(row + 1, col))
				move = false;
			break;

		// Check for space to the left
		case LEFT: 
			if (row == (Grid.HEIGHT - 1) || grid.isSet(row + 1, col) ||
			col == (0) || grid.isSet(row, col - 1)) 
				move = false;
			break;
			
		// Check for space to the right	
		case RIGHT:
			if (row == (Grid.HEIGHT - 1) || grid.isSet(row + 1, col) ||
			col == (Grid.WIDTH - 1) || grid.isSet(row, col + 1) )
				move = false;
			break;
		}
		return move;
	}

	/**
	 * moves this square in the given direction if possible.
	 * 
	 * The square will not move if the direction is blocked, or if the square is
	 * unable to move.
	 * 
	 * If it attempts to move DOWN and it can't, the square is frozen and cannot
	 * move anymore
	 * 
	 * @param direction
	 *            the direction to move
	 */
	public void move(Direction direction) {
		
		if (canMove(direction)) {
			switch (direction) {
			
			// DROP and DOWN both have same action, 
			// moving down a row (DROP is iterated in the Game class) 
			case DOWN:
			case DROP:
				row ++;
				break;
				
			// Moves left one column
				case LEFT:
				col --;
				break;
			
			// Moves right one column
			case RIGHT:
				col ++;
				break;	
			}
		}
	}

	/**
	 * Changes the color of this square
	 * 
	 * @param c
	 *            the new color
	 */
	public void setColor(Color c) {
		color = c;
	}

	/**
	 * Gets the color of this square
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Draws this square on the given graphics context
	 */
	public void draw(Graphics g) {

		// calculate the upper left (x,y) coordinate of this square
		int actualX = Grid.LEFT + col * WIDTH;
		int actualY = Grid.TOP + row * HEIGHT;
		g.setColor(color);
		g.fillRect(actualX, actualY, WIDTH, HEIGHT);
		// black border (if not empty)
		if (!color.equals(Grid.EMPTY)) {
			g.setColor(Color.BLACK);
			g.drawRect(actualX, actualY, WIDTH, HEIGHT);
		}
	}
	
	
	/** Checks that the square can rotate to its new location */
	public boolean canRotate(Square pivot) {
		
		// Determine what this Square's new position should be
		// given a 90 degree rotation about the pivot Square
		int rotatedRow = pivot.getRow() + (col - pivot.getCol());
		int rotatedCol = pivot.getCol() + (pivot.getRow() - row);
		
		// If the newly calculated row or column is outside of 
		// the bounds of the board, the square cannot rotate
		if (rotatedRow < 0 || rotatedRow > Grid.HEIGHT - 1 || rotatedCol < 0 || rotatedCol > Grid.WIDTH - 1) {
			return false;
		}
		
		// Find correct path of rotation for checking
		if (row < pivot.getRow()) { // Square's current position is somewhere above the pivot square
			
			// must be able to move right to the rotatedCol,
			// then down to the rotatedRow
			// check rights
			for (int i = col + 1; i <= rotatedCol; i++) {
				if (grid.isSet(row, i)) {
					return false;
				}
			}
			// all moves to the right are valid; check downs
			for (int i = row + 1; i <= rotatedRow; i++) {
				if (grid.isSet(i, rotatedCol)) {
					return false;
				}
			}
			// all moves for this square's path of rotation are valid
			
		} else if (row > pivot.getRow()) { // Square's current position is somewhere below the pivot square
			
			// must be able to move left to the rotatedCol,
			// then up to the rotatedRow
			// check lefts
			for (int i = col - 1; i >= rotatedCol; i--) {
				if (grid.isSet(row, i)) {
					return false;
				}
			}
			// all moves to the left are valid; check ups
			for (int i = row - 1; i >= rotatedRow; i--) {
				if (grid.isSet(i, rotatedCol)) {
					return false;
				}
			}
			// all moves for this square's path of rotation are valid
			
		// Square is neither above nor below the pivot Square!!//
			
		} else if (col < pivot.getCol()) { // Square's current position is somewhere to the left of the pivot Square
			
			// must be able to move up to the rotatedRow, 
			// then right to the rotatedCol
			// check ups
			for (int i = row - 1; i >= rotatedRow; i--) {
				if (grid.isSet(i, col)) {
					return false;
				}
			}
			// all moves up are valid; check rights
			for (int i = col + 1; i <= rotatedCol; i++) {
				if (grid.isSet(rotatedRow, i)) {
					return false;
				}
			}
			// all moves for this square's path of rotation are valid
			
		} else if (col > pivot.getCol()) { //Square's current position is somewhere to the right of the pivot Square
			
			// must be able to move down to the rotatedRow,
			// then left to the rotatedCol
			// check downs
			for (int i = row + 1; i <= rotatedRow; i++) {
				if (grid.isSet(i, col)) {
					return false;
				}
			}
			// all moves down are valid; check lefts
			for (int i = col - 1; i >= rotatedCol; i--) {
				if (grid.isSet(rotatedRow, i)) {
					return false;
				}
			}
			// all moves for this square's path of rotation are valid
		}
		// If execution gets here, the Square's path of rotation is clear, 
		// or it IS the pivot square
		return true;		
	}
	
	/** Reassigns position to rotated position (only called if rotated position is valid) */
	public void rotate (Square pivot) {
		int rotatedRow = pivot.getRow() + (col - pivot.getCol());
		int rotatedCol = pivot.getCol() + (pivot.getRow() - row);
		row = rotatedRow;
		col = rotatedCol;
	}
}
