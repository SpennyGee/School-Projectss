package homework04;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

/** 
 * The Controller handles the mouse clicks by the user 
 * and sends appropriate messages to the model
 */
public class Controller implements ActionListener, MouseListener {
	
	// The drawing model
	private DrawingModel model;
	
	// boolean for determining whether or not to add a level 
	private boolean addLevel;

	// Connect controller and model (in the constructor)
	public Controller(DrawingModel model) {
		this.model = model;
	}

	/**  
	 * what happens when one of the three buttons is clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// if the "Add level" button is clicked, addLevel is set to true
		if (e.getActionCommand().contains("Add")) {
			addLevel = true;
			
		// if "Remove level" button is clicked, addLevel is set to false
		} else if (e.getActionCommand().contains("Remove")) {
			addLevel = false;
			
		// if the reset button is clicked, then the shapes in the model reset back to their base level
		} else {
			
			// resets the model
			boolean success = model.resetLevel();
			
			// if either of the shapes change from the reset, then the model updates
			if(success) {
				
				// updates the model
				model.updateAll();
			}
		}
	}

	/** 
	 * what happens when the window is clicked on 
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
		// if addLevel is true then the model adds a level if it can
		if (addLevel) {
			
			// attempts to add a level
			// boolean equals true if it successfully added the level, false if not
			boolean success = model.addLevel(e.getX(), e.getY());
			
			// if the add level attempt is unsuccessful then a message is displayed
			if (!success) {
				
				// displays a message box stating that no more levels can be added
				JOptionPane.showMessageDialog( null ,"No more levels can be added","Notice:", JOptionPane.INFORMATION_MESSAGE);
				
			// if the add level attempt was a success the model updates
			} else if(success) {
				
				// updates the model
				model.updateAll();
			}
			
		// if addLevel is false then the model removes a level if it can
		} else if(!addLevel) {
			
			// attempts to remove a level
			// boolean equals true if it successfully removed the level, false if it didn't
			boolean success = model.removeLevel(e.getX(), e.getY());
			
			// if the remove level attempt is unsuccessful then a message is displayed
			if (!success) {
				
				// displays a message box stating that no more levels can be removed
				JOptionPane.showMessageDialog( null ,"You cannot remove anymore levels","Notice:", JOptionPane.INFORMATION_MESSAGE);
				
			// if the remove level attempt was a success the model updates
			} else if(success) {
				
				// updates the model
				model.updateAll();
			}
		}
	}
	

	// Not used
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}