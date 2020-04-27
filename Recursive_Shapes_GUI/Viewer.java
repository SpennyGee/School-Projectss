package homework04;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Viewer updates the window that the shapes are displayed in
 */
public class Viewer extends JPanel implements View {
	
	// The model this View is connected to
	private DrawingModel model;

	// sets the background to white
	public Viewer() {
		setBackground(Color.WHITE);
	}

	/** updates the DrawingModel */
	@Override
	public void update(DrawingModel model) {
		this.model = model;
		repaint();
	}

	/** Paints the contents of this viewer */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// if there is a model it draws all of the shapes in the model
		if (model != null) {
			for (Shape s : model.getShapes()) {
				s.draw(g);
			}
		}
	}
}