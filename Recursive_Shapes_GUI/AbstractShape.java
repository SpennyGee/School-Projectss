package homework04;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Abstract class that shapes are connected to through hierarchy
 */
public abstract class AbstractShape implements Shape {
	
	// color of the shape
	protected Color color;
	
	// XCoordinate of the shape
	protected int xCoord;
	
	// YCoordinate of the shape
	protected int yCoord;
	
	// Height and width of the shape (square border) 
	protected int size;
		
	// Array of children shapes for the Shape
	protected Shape[] children;
	
	// The level of the current shape
	protected int level;
	
	/**
	 * Instantiates basic shape fields
	 * 
	 * @param x: XCoordinate
	 * @param y: YCoordinate
	 * @param c: color
	 * @param size: height and with of each square
	 */
	public AbstractShape(int x, int y, Color c, int size) {
		
		this.xCoord = x;
		this.yCoord = y;
		this.color = c;
		this.size = size;
		
		this.level = 1;
	}
	
	// the shape can use a draw method
	public void draw(Graphics g) {	
	}
	
	/** adds a new level to the given shape 
	 * 
	 * @return canCreate: whether or not a level can be added
	 */
	@Override
	public boolean addLevel() {
		
		// initial boolean set to true
		boolean canCreate = true;
		
		try {
			// recurses through the method until it reaches a shape child that doesn't have any children
			// then a NullPointException is given and it moves to the catch statement
			for (Shape child : children) {
				canCreate = child.addLevel();
			}
			
			// adds 1 to the level count as long as a new level is added
			if(canCreate) {
			level ++;
			}
		
		// if the child shape doesn't have any children new children are created for it
		} catch(NullPointerException e) {
			
			canCreate = createChildren();
			
			// adds 1 to the level count as long as a new level is added
			if(canCreate) {
			level ++;
			}
		}
		
		// returns the boolean
		return canCreate;
	}
	
	/** removes the most recent level of the given shape
	 * 
	 * @return canRemove: whether or not a level can be removed
	 */
	public boolean removeLevel() {
		
		// initial boolean set to true
		boolean canRemove = true;
		
		try {
			
			// recurses through the method until it reaches a shape child whose children doesn't have any children
			// then it removes the children that don't have any children
			for (Shape child : children) {
				if(child.hasChildren() == true) {
					child.removeLevel();	
				} else {
					deleteChildren();
				}
			}
			
			// removes 1 from the level count
			level --;
		
		// if the child shape doesn't have any children then no levels can be removed
		} catch(NullPointerException e) {
			
			// boolean set to false
			canRemove = false;
			
			// removes 1 from the level count as long as it's not already at the base level
			if(level > 1) {
				level --;
				
				// means that it hasn't reached the base level so it can remove
				canRemove = true;
			}
		}
		
		// returns the boolean
		return canRemove;
	}
	
	
	/** determines if the given level has another level after it 
	 * 
	 * @return a: whether or not the child shapes in the children array have children 
	 */
	public boolean hasChildren() {
		
		// initial boolean set to true
		boolean a = true;
			
			// if any of the child shapes in the children array don't have 
			// any children shapes of their own then the boolean is set to false
			for(Shape child : children) {
				if(child == null) {
					a = false;
				}
			}
			
		// returns the boolean
		return a;
	}
	
	/** resets the given shape back to its base level
	 * 
	 * @return bool: whether or not the amount of levels of the shape changed
	 * 					if the shape was already at the base level it should
	 * 					return false, otherwise it should return true
	 */
	public boolean resetLevel() {
		
		// initial boolean set to true
		boolean bool = true;
		
		// if the initial level was already 1 the shape doesn't change
		// and the boolean gets set to false
		if(level == 1) {
			bool = false;
		}
		
		// deletes all of the children for the shape
		deleteChildren();
		
		// level gets reset to 1
		level = 1;
		
		// always returns true
		return bool;
	}

	/** gives the type of the shape, the coordinates of the shape, its color, and its current level
	 * 
	 *  @return String: information about the shape
	 */
	@Override
	public String toString() {
		return "\n\nThis shape is a: " + getClass() + "\nThe x coordinate is: " + xCoord + "\nThe y coordinate is: " + yCoord
				+ "\nThe color is: " + color + "\nThe current level is: " + level + "\n";
	}
}