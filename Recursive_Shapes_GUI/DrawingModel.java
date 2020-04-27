package homework04;
import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;

/**
 * DrawingModel holds the state of the drawing
 * it maintains a collection of shapes and views
 */
public class DrawingModel {
	
	// an ArrayList of shapes
	private ArrayList<Shape> shapes = new ArrayList<Shape>(); 
	
	// an ArrayList of views
	private ArrayList<View> views = new ArrayList<View>();
	
	/** adds a View to the views ArrayList */ 
	public void addView(View v) {
		
		// adds a View to the views ArrayList
		views.add(v);
		// updates the View
		v.update(this);
	}
	
	/** updates the views */
	public void updateAll() {
	
		// updates all of the views in the ArrayList
		for(View v : views) {
			v.update(this);
		}
	}
	
	/** adds a Shape to the shape ArrayList */
	public void addShape(Shape s) {
		
		// adds a shape to the shapes ArrayList
		shapes.add(s);
		// calls updateAll() to update all of the views
		updateAll();
	}

	/** returns the ArrayList of shapes */
	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	
	/** 
	 * adds a level to the shapes that contain the given x and y coordinates
	 * 
	 * @param x: the given x coordinate
	 * @param y: the given y coordinate
	 * 
	 * @return b: whether or not a level was added
	 */
	public boolean addLevel(int x, int y) {
		
		// initially set to true
		boolean b = true;
		
		// iterates through all of the shapes in the window
		for(Shape s : shapes) {
			
			// if (x,y) is within the area that the given shape contains, add a level to that shape
			if (s.contains(x,y)) {
				b = s.addLevel();
			}
		}
		
		// returns true if a new level was added, false if not
		return b;
	}
	
	/** 
	 * removes a level of the shapes that contain the given x and y coordinates
	 * 
	 * @param x: the given x coordinate
	 * @param y: the given y coordinate
	 * 
	 * @return b: whether or not a level was removed
	 */
	public boolean removeLevel(int x, int y) {
		
		// initially set to true
		boolean b = true;
		
		// iterates through all of the shapes in the window
		for(Shape s : shapes) {
			
			// if (x,y) is within the area that the given shape contains, remove a level from that shape
			if (s.contains(x,y)) {
				b = s.removeLevel();
			}
		}
		
		// returns true if a level was removed, false if not
		return b;
	}
	
	/** 
	 * resets the shapes to their level and appearance 
	 * 
	 * @return b: whether or not at least one of the shape's levels were reset
	 */
	public boolean resetLevel() {
		
		// initially set to false
		boolean b = false;
		boolean a = false;
		
		// iterates through all of the shapes in the window
		for(Shape s : shapes) {
			// resets the shape to it's base level
			a = s.resetLevel();
				
			// if a or b is true b is true
			// because even if one shape changes the model gets updated
			if(a || b) {
				b = true;
			}
		}
		
		// always returns true since resetLevel() always returns true
		return b;
	}
}