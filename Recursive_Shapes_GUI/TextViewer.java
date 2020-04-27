package homework04;

import javax.swing.JPanel;

/**
 *  TextViewer prints to the terminal window the information about the shapes 
 *  as given by the toString method of a shape. It does this when the model updates,
 *  which happens when the model changes
 */
public class TextViewer implements View {

	/** updates the DrawingModel */
	@Override
	public void update(DrawingModel model) {
		
		// if there is a model this calls the toString() method in the AbstractShape class for each shape
		if (model != null) {
			for (Shape s : model.getShapes()) {
				System.out.println(s.toString());
			}
		}
	}
}
