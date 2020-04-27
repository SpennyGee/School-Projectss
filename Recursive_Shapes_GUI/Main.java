package homework04;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/** This program meets all of the requirements for the assignment */

/**
 * The main class that creates a GUI which displays HShape and FibonacciSquare
 * 
 * @author CSC 143
 */
public class Main {

	/** calls a method that assembles a GUI */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			createGUI();
		});
	}

	/** creates a GUI with HShapes and FibonacciSquares */
	public static void createGUI() {
		
		// Creates a new JFrame
		JFrame frame = new JFrame();
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(0, 0);

		// Creates a visible viewer panel
		Viewer panel = new Viewer();
		frame.add(panel);
		
		// Buttons at the top of the frame
		JRadioButton add = new JRadioButton("Add level");
		JRadioButton remove = new JRadioButton("Remove level");
		JButton reset = new JButton("Reset");
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.add(add);
		northPanel.add(remove);
		northPanel.add(reset);
		frame.add(northPanel, BorderLayout.NORTH);
		
		// Connect the two radio buttons and select the add button
		ButtonGroup group = new ButtonGroup();
		group.add(add);
		group.add(remove);
		add.setSelected(true);
		
		// Starting location for the FibonacciSquare
		Shape fibStart = new FibonacciSquare(250, 220, Color.red, 1, 1);
		
		// Starting location for the HShape
		Shape hStart = new HShape(560, 50, Color.green, 400);
		
		// creates a new DrawingModel
		DrawingModel model = new DrawingModel();
		model.addView(panel);
		
		// Create a controller
		Controller controller = new Controller(model);
		
		
		// The controller listens to the button clicks
		add.addActionListener(controller);
		remove.addActionListener(controller);
		reset.addActionListener(controller);
		
		// The controller listens to the mouse clicks on the display panel
		panel.addMouseListener(controller);
		
		// adds the fibonacci square to the model
		model.addShape(fibStart);

		// adds the HShape to the model
		model.addShape(hStart);
		
		// adds the panel to the model
		model.addView(panel);
		
		// new TextViewer
		TextViewer textView = new TextViewer();
		
		// adds the TextViewer to the model
		model.addView(textView);
		
		// makes the frame visible
		frame.setVisible(true);
	}
}