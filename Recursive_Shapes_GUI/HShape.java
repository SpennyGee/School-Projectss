package homework04;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Creates a HShape out of squares
 */
public class HShape extends AbstractShape {
	
	// height of each square used to make the HShape
	private int squareHeight;
	
	/**
	 * Creates a HShape
	 * 
	 * @param x: xCoordinate
	 * @param y: yCoordinate
	 * @param c: color
	 * @param size: height and with of the whole HShape
	 */
	public HShape(int x, int y, Color c, int size) {
		super(x,y,c,size);
		
		this.squareHeight = size/3;
		children = new Shape[7];
	}

	/** draws the HShape 
	 * 
	 * @param g: Graphics for the HShape
	 */
	@Override
	public void draw(Graphics g) {
		
		// sets the color of the squares used to make the HShape
		g.setColor(color);
		
		// draws the squares that make up the HShape
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
		
				// Sets the squares that aren't part of the HShape to white
				if((row == 0 || row == 2) && col == 1) {
					g.setColor(Color.white);
				}
				
				// draws the HShapes in the children array
				try {
					for (Shape s : children) {
						s.draw(g);	
					} 
					
				// draws the base case if there aren't any added shapes in the array
				} catch(NullPointerException e) {
				
					// fills in the squares that are part of the HShape to the selected color
					g.fillRect(xCoord + (col * squareHeight), yCoord + (row * squareHeight), squareHeight, squareHeight);
					
					// sets the color to black
					g.setColor(Color.black);
					
					// draws a black outline for all of the squares
					g.drawRect(xCoord + (col * squareHeight), yCoord + (row * squareHeight), squareHeight, squareHeight);
					
					// sets the color back to the original selected color
					g.setColor(color);
				}
			}
		}
	}
	
	/** Deletes the current level of the HShape so it returns to its previous level */
	@Override
	public void deleteChildren() {
		
		// sets all of the children in the most recent level to null
		// this returns the shape to its previous level
		for(int index = 0; index < children.length; index ++) {
			children[index] = null;	
		}
	}
	
	/** Creates a new fractal level for the HShape for the given HShape 
	 * 
	 * @return bool: whether or not children were created
	 */
	@Override
	public boolean createChildren() {
		
		// initial boolean set to false
		boolean bool = false;
		
		// int used navigating indexes in the children array
		int index = 0;
		
		// iterates through the nine different sections of the given HShape
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {	
				
				// doesn't add anything in the "blank" areas of the HShape
				// or if the children shapes get too small
				if(!((row == 0 || row == 2) && col == 1) && squareHeight >= 25)  {
					
					// Creates a new HShape child at the new level
					HShape child = new HShape(xCoord + (col * squareHeight), yCoord + (row * squareHeight), color, squareHeight);
					
					// adds the new child to the children index
					children[index] = child;
					
					// updates the index number for the next iteration
					index++;
					
					// if it creates new children the boolean is set to true
					bool = true;
				}
			}
		}
		
		// returns the boolean
		return bool;
	}
	
	/** 
	 * determines if the given location contains a HShape
	 * 
	 * @param x: x coordinate of given location
	 * @param y: y coordinate of given location
	 * 
	 * @return boolean: whether or not the shape area contains the given coordinates 
	 */
	@Override
	public boolean contains(int x, int y) {
	
		// returns true if the given location is within the right half of the window
		if ( (x > 500 && x < 1000) && (y > 0 && y < 600)) {
			return true;
		} else {
			return false;
		}
	}
}