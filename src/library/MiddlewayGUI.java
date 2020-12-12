package library;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MiddlewayGUI extends JFrame {
	private MiddlewayPanel middlewayPanel;
	
	public MiddlewayGUI(String user, String firstName) {
		// Set the title.
		setTitle("Dashboard");
		setMinimumSize(new Dimension(600, 300));
		setLocationRelativeTo(null);
		
		// Specify an action for the close button.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create a BorderLayout manager.
		setLayout(new BorderLayout());
		
		// Build the panels.
		middlewayPanel = new MiddlewayPanel(this, user, firstName);
		
		// Add the panels to the content pane.
		add(middlewayPanel, BorderLayout.CENTER);
		
		// Pack and display the window.
		pack();
		setVisible(true);
	}
	
//	/**
//		main method
//	*/
//	public static void main(String[] args) 
//	{
//		new MiddlewayGUI();
//	}

}
