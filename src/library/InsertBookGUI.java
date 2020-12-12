package library;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InsertBookGUI extends JFrame {
	InsertBookPanel insertBookPanel;
	
	public InsertBookGUI(String user, String firstName) {
		// Set the title.
		setTitle("Dashboard");
		setMinimumSize(new Dimension(600, 300));
		setLocationRelativeTo(null);
		
		// Specify an action for the close button.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create a BorderLayout manager.
		setLayout(new BorderLayout());
		
		// Build the panels.
		insertBookPanel = new InsertBookPanel(this, user, firstName);
		
		// Add the panels to the content pane.
		add(insertBookPanel, BorderLayout.CENTER);
		
		// Pack and display the window.
		pack();
		setVisible(true);
	}

}
