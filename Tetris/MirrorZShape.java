package Homework02;
import java.awt.Color;

/**
 * A MirrorZShape piece in the Tetris Game.
 * 
 * @author CSC 143
 *
 */
public class MirrorZShape extends AbstractPiece {

	public MirrorZShape(int r, int c, Grid g) {
		
		super(r,c,g);

		// Create the squares
		square[0] = new Square(g, r - 1, c - 1, Color.red, true);
		square[1] = new Square(g, r - 1, c, Color.red, true);
		square[2] = new Square(g, r, c, Color.red, true);
		square[3] = new Square(g, r, c + 1, Color.red, true);
	}
}
