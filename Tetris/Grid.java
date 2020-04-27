package Homework02;
import java.awt.Color;
import java.awt.Graphics;

/**
 * This is the Tetris board represented by a (HEIGHT - by - WIDTH) matrix of
 * Squares.
 * 
 * The upper left Square is at (0,0). The lower right Square is at (HEIGHT -1,
 * WIDTH -1).
 * 
 * Given a Square at (x,y) the square to the left is at (x-1, y) the square
 * below is at (x, y+1)
 * 
 * Each Square has a color. A white Square is EMPTY; any other color means that
 * spot is occupied (i.e. a piece cannot move over/to an occupied square). A
 * grid will also remove completely full rows.
 * 
 * @author CSC 143
 */
public class Grid {
	private Square[][] board;

	// Width and Height of Grid in number of squares
	public static final int HEIGHT = 20;

	public static final int WIDTH = 10;

	private static final int BORDER = 5;

	public static final int LEFT = 100; // pixel position of left of grid

	public static final int TOP = 50; // pixel position of top of grid

	public static final Color EMPTY = Color.WHITE;

	/**
	 * Creates the grid
	 */
	public Grid() {
		board = new Square[HEIGHT][WIDTH];

		// put squares in the board
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				board[row][col] = new Square(this, row, col, EMPTY, false);
			}
		}
	}

	/**
	 * Returns true if the location (row, col) on the grid is occupied
	 * 
	 * @param row
	 *            the row in the grid
	 * @param col
	 *            the column in the grid
	 */
	public boolean isSet(int row, int col) {
		
		if(row >= 0 && row < Grid.HEIGHT && col >= 0 && col < Grid.WIDTH) {
			return !board[row][col].getColor().equals(EMPTY);
		}else {
			return false;
		}
	}

	/**
	 * Changes the color of the Square at the given location
	 * 
	 * @param row
	 *            the row of the Square in the Grid
	 * @param col
	 *            the column of the Square in the Grid
	 * @param c
	 *            the color to set the Square
	 * @throws IndexOutOfBoundsException
	 *             if row < 0 || row>= HEIGHT || col < 0 || col >= WIDTH
	 */
	public void set(int row, int col, Color c) {
		board[row][col].setColor(c);
	}

	/**
	 * Checks for and remove all solid rows of squares.
	 * 
	 * If a solid row is found and removed, all rows above it are moved down and
	 * the top row set to empty
	 */
	public void checkRows() {
		
		// iterates through all of the rows in the grid
		for(int row = 0; row < HEIGHT; row ++) {
			
			// boolean used to determine if the row is filled or not
			boolean checkFull = true;
			
			// iterates through all of the columns in the grid
			for(int col = 0; col < WIDTH; col ++) {
				
				// if one of squares in the selected row is empty boolean full = false
				checkFull &= isSet(row, col);
			}
			
			// if the row is full this removes that row, and then shifts the other rows above it down my 1
			if(checkFull) {
				
				// iterates through all of the rows at and above the filled row
				for(int fullRow = row; fullRow >= 0; fullRow --) {
					
					// iterates through all of the squares within the selected row 
					for(int col = 0; col < WIDTH; col ++) {

						// sets the entire top row to white
						if(fullRow == 0) {
							this.set(0, col, EMPTY);
						}else {
							// changes the color of the selected square to that of the one above it
							this.set(fullRow, col, board[fullRow-1][col].getColor());
						}
					}
				}
			}	
		}
	}

	/**
	 * Draws the grid on the given Graphics context
	 */
	public void draw(Graphics g) {

		// draw the edges as rectangles: left, right in blue then bottom in red
		g.setColor(Color.BLUE);
		g.fillRect(LEFT - BORDER, TOP, BORDER, HEIGHT * Square.HEIGHT);
		g.fillRect(LEFT + WIDTH * Square.WIDTH, TOP, BORDER, HEIGHT
				* Square.HEIGHT);
		g.setColor(Color.RED);
		g.fillRect(LEFT - BORDER, TOP + HEIGHT * Square.HEIGHT, WIDTH
				* Square.WIDTH + 2 * BORDER, BORDER);

		// draw all the squares in the grid
		// empty ones first (to avoid masking the black lines of the pieces that
		// have already fallen)
		for (int r = 0; r < HEIGHT; r++) {
			for (int c = 0; c < WIDTH; c++) {
				if (board[r][c].getColor().equals(EMPTY)) {
					board[r][c].draw(g);
				}
			}
		}
		for (int r = 0; r < HEIGHT; r++) {
			for (int c = 0; c < WIDTH; c++) {
				if (!board[r][c].getColor().equals(EMPTY)) {
					board[r][c].draw(g);
				}
			}
		}
	}
}