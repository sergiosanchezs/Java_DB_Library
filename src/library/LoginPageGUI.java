package library;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginPageGUI extends JFrame {
	private LoginPanel loginPanel;
	
	public LoginPageGUI() {
		// Set the title.
		setTitle("Login Page");
		setMinimumSize(new Dimension(300, 300));
		setLocationRelativeTo(null);
		
		// Specify an action for the close button.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create a BorderLayout manager.
		setLayout(new BorderLayout());
		
		// Build the panels.
		loginPanel = new LoginPanel(this);
		
		// Add the panels to the content pane.
		add(loginPanel, BorderLayout.CENTER);
		
		// Pack and display the window.
		pack();
		setVisible(true);
	}
	
	/**
		main method
	*/
	public static void main(String[] args) 
	{
		new LoginPageGUI();
	}

}