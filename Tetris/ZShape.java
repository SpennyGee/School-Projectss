package Homework02;
import java.awt.Color;

/**
 * A ZShape piece in the Tetris Game.
 * 
 * @author CSC 143
 *
 */
public class ZShape extends AbstractPiece {

	public ZShape(int r, int c, Grid g) {
		
		super(r,c,g);

		// Create the squares
		square[0] = new Square(g, r - 1, c + 1, Color.green, true);
		square[1] = new Square(g, r - 1, c, Color.green, true);
		square[2] = new Square(g, r, c, Color.green, true);
		square[3] = new Square(g, r, c - 1, Color.green, true);
	}
}