package Homework02;
import java.awt.Color;

/**
 * A Bar piece in the Tetris Game.
 * 
 * @author CSC 143
 *
 */
public class Bar extends AbstractPiece {
	
	public Bar(int r, int c, Grid g) {
		
		super(r,c,g);

		// Create the squares
		square[0] = new Square(g, r - 1, c - 1, Color.CYAN, true);
		square[1] = new Square(g, r - 1, c , Color.CYAN, true);
		square[2] = new Square(g, r - 1, c + 1, Color.CYAN, true);
		square[3] = new Square(g, r - 1, c + 2, Color.CYAN, true);
	}
}