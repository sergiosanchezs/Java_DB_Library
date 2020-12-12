package library;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BooksPageGUI extends JFrame {
	private BorrowReturnPanel borrowReturn;
	
	public BooksPageGUI(String action, String user, String firstN) {
		// Set the title.
		setTitle("Library");
		setMinimumSize(new Dimension(600, 300));
		setLocationRelativeTo(null);
		
		// Specify an action for the close button.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create a BorderLayout manager.
		setLayout(new BorderLayout());
		
		// Build the panels.
		borrowReturn = new BorrowReturnPanel(this, action, user, firstN);
		
		// Add the panels to the content pane.
		add(borrowReturn, BorderLayout.CENTER);
		
		// Pack and display the window.
		pack();
		setVisible(true);
		
	}
	
//	/**
//		main method
//	*/
//	public static void main(String[] args) 
//	{
//		new BooksPageGUI(action);
//	}

}
