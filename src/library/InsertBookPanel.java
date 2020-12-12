package library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertBookPanel extends JPanel {
	InsertBookGUI bookGUI;
	String username, firstName;
	JLabel welcomeMsg, titleLbl, authorLbl, isbnLbl, publisherLbl;
	JTextField title, author, isbn, publisher;
	JButton insertBtn, goBackBtn;
	
	public InsertBookPanel(InsertBookGUI bkGUI, String user, String firstN) {
		bookGUI = bkGUI;
		username = user;
		firstName = firstN;

		setLayout(new GridLayout(13, 1));
		
		setBorder(BorderFactory.createTitledBorder("Dashboard"));
		
		welcomeMsg =  new JLabel("Welcome " + firstName);
		titleLbl = new JLabel("Title");
		authorLbl = new JLabel("Author");
		isbnLbl = new JLabel("ISBN");
		publisherLbl = new JLabel("Publisher");
		
		title = new JTextField();
		author = new JTextField();
		isbn = new JTextField();
		publisher = new JTextField();
		
		insertBtn = new JButton("Insert");
		insertBtn.addActionListener(new insert_btn());
		
		goBackBtn = new JButton("Go Back");
		goBackBtn.addActionListener(new go_back_btn());
		
		add(welcomeMsg);
		add(titleLbl);
		add(title);
		add(authorLbl);
		add(author);
		add(isbnLbl);
		add(isbn);
		add(publisherLbl);
		add(publisher);
		add(insertBtn);
		add(goBackBtn);
		
	}
	
	private class go_back_btn implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			new MiddlewayGUI(username, firstName);
			bookGUI.dispose();
		}
	}
	
	private class insert_btn implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			
			try {
				
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
				
				Statement stmt = conn.createStatement();
				
				String query = "INSERT INTO books (isbn, title, author, publisher, status) "
						+ "VALUES (" + isbn.getText() + ", "
								+ "'" + title.getText() +  "', "
								+ "'" + author.getText() +  "', "
								+ "'" + publisher.getText() +  "', "
								+ "1)";
				
				int upRows = stmt.executeUpdate(query);
				
				if (upRows > 0) {
					JOptionPane.showMessageDialog(null, "Your book was successfully inserted");
					new MiddlewayGUI(username, firstName);
					bookGUI.dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "Error: Your book was NOT inserted");
				
				conn.close();			
				
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Coudn't connect to DB");
			}
			
		}
	}
}
