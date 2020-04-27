package Homework02;
import java.awt.Color;

/**
 * A Block piece in the Tetris Game.
 * 
 * @author CSC 143
 *
 */
public class Block extends AbstractPiece {
	
	public Block(int r, int c, Grid g) {
		
		super(r,c,g);

		// Create the squares
		square[0] = new Square(g, r - 1, c - 1, Color.GRAY, true);
		square[1] = new Square(g, r - 1, c, Color.GRAY, true);
		square[2] = new Square(g, r, c - 1, Color.GRAY, true);
		square[3] = new Square(g, r, c, Color.GRAY, true);
	}

	/** Overrides the rotate method in AbstractPiece so block does not rotate */
	@Override
	public void rotate() {
	}

}