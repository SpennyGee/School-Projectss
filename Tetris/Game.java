package Homework02;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

/**
 * Manages the game Tetris. Keeps track of the current piece and the grid.
 * Updates the display whenever the state of the game has changed.
 * 
 * @author CSC 143
 */
public class Game {

	private Grid grid; // the grid that makes up the Tetris board

	private Tetris display; // the visual for the Tetris game

	private Piece piece; // the current piece that is dropping

	private boolean isOver; // has the game finished?
	
	private Random randomNumber = new Random(); // for random piece generation

	/**
	 * Creates a Tetris game
	 * 
	 * @param Tetris
	 *            the display
	 */
	public Game(Tetris display) {
		grid = new Grid();
		this.display = display;
		piece = createPiece();
		isOver = false;
	}

	/**
	 * Draws the current state of the game
	 * 
	 * @param g
	 *            the Graphics context on which to draw
	 */
	public void draw(Graphics g) {
		grid.draw(g);
		if (piece != null) {
			piece.draw(g);
		}
	}

	/**
	 * Moves the piece in the given direction
	 * 
	 * @param the
	 *            direction to move
	 */
	public void movePiece(Direction direction) {
		
		// the movement for the piece in the drop direction
		// when the space bar is pressed
		if (direction == Direction.DROP) {
			while (piece != null) {
				piece.move(direction);
				updatePiece();
			}
			
		// the movement of the piece for the left, right, and down directions
		} else if (piece != null) {
			piece.move(direction);
		}
		
		// updates the piece and displays the updated piece
		updatePiece();
		display.update();
	}

	/**
	 * Returns true if the game is over
	 */
	public boolean isGameOver() {
		
		// game is over if the piece occupies the same space as some non-empty
		// part of the grid. Usually happens when a new piece is made
		if (piece == null) {
			return false;
		}

		// check if game is already over
		if (isOver) {
			return true;
		}

		// check every part of the piece
		Point[] p = piece.getLocations();
		for (int i = 0; i < p.length; i++) {
			if (grid.isSet((int) p[i].getX(), (int) p[i].getY())) {
				isOver = true;
				return true;
			}
		}
		return false;
	}

	/** Updates the piece */
	private void updatePiece() {
		
		if (piece == null) {
			// CREATE A NEW PIECE HERE
			grid.checkRows();
			piece = createPiece();
		}

		// set Grid positions corresponding to frozen piece
		// and then release the piece
		else if (!piece.canMove(Direction.DOWN)) {
			Point[] p = piece.getLocations();
			Color c = piece.getColor();
			for (int i = 0; i < p.length; i++) {
				grid.set((int) p[i].getX(), (int) p[i].getY(), c);
			}
			piece = null;
		}
	}
	
	
	/**
	 * Moves the piece in the given direction
	 * 
	 * @param the
	 *            direction to move
	 */
	public void rotatePiece() {
		
		// rotate() method is called if the piece is not null
		if (piece != null) {
			piece.rotate();
			
	}
		// updates the piece and then displays the update
		updatePiece();
		display.update();
		
	}
	
	/** Creates and then returns a new piece */
	private Piece createPiece() {		
		
		// makes a new piece
		Piece newPiece = null;
		
		// uses a random number to determine which piece is generated
		switch (randomNumber.nextInt(7)) {
		case 0:
			newPiece = new ZShape(1, Grid.WIDTH / 2 - 1, grid);
			break;
		case 1:
			newPiece = new Block(1, Grid.WIDTH / 2 - 1, grid);
			break;
		case 2:
			newPiece = new MirrorLShape(1, Grid.WIDTH / 2 - 1, grid);
			break;
		case 3:
			newPiece = new TShape(1, Grid.WIDTH / 2 - 1, grid);
			break;
		case 4:
			newPiece =  new MirrorZShape(1, Grid.WIDTH / 2 - 1, grid);
			break;
		case 5:
			newPiece = new Bar(1, Grid.WIDTH / 2 - 1, grid);
			break;
		case 6: 
			newPiece = new LShape(1, Grid.WIDTH / 2 - 1, grid);
			break;
		}
		
		// returns the generated piece
		return newPiece;
	}
}