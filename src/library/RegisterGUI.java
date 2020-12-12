package library;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RegisterGUI extends JFrame {
	RegisterPanel registerPanel;
	
	public RegisterGUI() {
		// Set the title.
		setTitle("Dashboard");
		setMinimumSize(new Dimension(300, 300));
		setLocationRelativeTo(null);
		
		// Specify an action for the close button.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create a BorderLayout manager.
		setLayout(new BorderLayout());
		
		// Build the panels.
		registerPanel = new RegisterPanel(this);
		
		// Add the panels to the content pane.
		add(registerPanel, BorderLayout.CENTER);
		
		// Pack and display the window.
		pack();
		setVisible(true);
	}
}
