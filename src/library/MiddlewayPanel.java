package library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiddlewayPanel extends JPanel {
	JButton borrowBtn, returnBtn, logOutBtn, insertBookBtn;
	MiddlewayGUI midGUI;
	String username, firstName;
	JLabel welcomeMsg;
	
	public MiddlewayPanel(MiddlewayGUI middleGUI, String user, String firstN) {
		// Create a GridLayout manager with
		// 3 rows and 1 column.
		username = user;
		firstName = firstN;
		midGUI = middleGUI;
		setLayout(new GridLayout(8, 1));
		
		setBorder(BorderFactory.createTitledBorder("Dashboard"));
		
		welcomeMsg =  new JLabel("Welcome " + firstName);
		borrowBtn = new JButton("Borrow");
		returnBtn = new JButton("Return");
		logOutBtn = new JButton("LogOut");
		insertBookBtn = new JButton("Insert a Book");
		borrowBtn.addActionListener(new borrow_btn());
		returnBtn.addActionListener(new return_btn());
		logOutBtn.addActionListener(new log_out_btn());
		insertBookBtn.addActionListener(new insert_book_btn());
		
		add(welcomeMsg);
		add(insertBookBtn);
		add(borrowBtn);
		add(returnBtn);
		add(logOutBtn);
		
	}
	
	private class insert_book_btn implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			new InsertBookGUI(username, firstName);
			midGUI.dispose();
		}
	}
	
	private class borrow_btn implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			new BooksPageGUI("Borrow", username, firstName);
			midGUI.dispose();
		}
	}
	
	private class return_btn implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			new BooksPageGUI("Return", username, firstName);
			midGUI.dispose();
		}
	}
	
	private class log_out_btn implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			new LoginPageGUI();
			midGUI.dispose();
		}
	}

}
