package Homework02;
import java.awt.Color;

/**
 * A TShape piece in the Tetris Game.
 * 
 * @author CSC 143
 *
 */
public class TShape extends AbstractPiece {

	public TShape(int r, int c, Grid g) {
		
		super(r,c,g);

		// Create the squares
		square[0] = new Square(g, r - 1, c - 1, Color.yellow, true);
		square[1] = new Square(g, r - 1, c, Color.yellow, true);
		square[2] = new Square(g, r - 1, c + 1, Color.yellow, true);
		square[3] = new Square(g, r, c, Color.yellow, true);
	}
}