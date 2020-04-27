package homework04;
import java.awt.Graphics;

/**
 * Interface that all of the shapes use
 * below are the methods that each shape can use
 */
public interface Shape {
	
	// a method that draws the shape
	void draw(Graphics g);
	
	// determines if the given location in the window is within the shape's area
	boolean contains(int x, int y);
	
	
	// adds a new level to the shape
	boolean addLevel();
	
	// removes the most recent level from the shape
	boolean removeLevel();
	
	// creates a new level for the shape
	boolean createChildren();
	
	// deletes the most recent level from the shape
	void deleteChildren();
	
	
	// resets the shape back to its base level
	boolean resetLevel();
	
	// determines if the given level has another level after it
	boolean hasChildren();
}