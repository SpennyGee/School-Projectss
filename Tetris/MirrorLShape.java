package Homework02;
import java.awt.Color;

/**
 * A MirrorLShape piece in the Tetris Game.
 * 
 * @author CSC 143
 *
 */
public class MirrorLShape extends AbstractPiece {

	public MirrorLShape(int r, int c, Grid g) {
		
		super(r,c,g);

		// Create the squares
		square[0] = new Square(g, r - 1, c, Color.blue, true);
		square[1] = new Square(g, r, c, Color.blue, true);
		square[2] = new Square(g, r + 1, c, Color.blue, true);
		square[3] = new Square(g, r + 1, c - 1, Color.blue, true);
	}
}