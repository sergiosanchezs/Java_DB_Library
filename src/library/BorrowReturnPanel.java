package library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BorrowReturnPanel extends JPanel {
	JComboBox bookComboBox;
	JLabel author, welcomeMsg, publisher;
	JButton borrowReturnBtn, goBackBtn;
	BooksPageGUI booksPageGUI;
	String username, firstName;
	int bookIndex;
	ArrayList<Book> bookList;
	String action;
	
	public BorrowReturnPanel(BooksPageGUI bookGUI, String act, String user, String firstN) {
		// Create a GridLayout manager with
		// 3 rows and 1 column.
		username = user;
		firstName = firstN;
		booksPageGUI =bookGUI;
		action = act;
		bookIndex = 0;
		
		setLayout(new GridLayout(8, 1));
		
		if (action.equalsIgnoreCase("borrow")) 
			bookList = getAvailableBooks();
		else 
			bookList = getBorrowedBooks();
		
		String[] books;

		books = new String[bookList.size()];
		
		for (int i = 0; i < bookList.size(); i++) {
			books[i] = bookList.get(i).getTitle();
		}
		
		bookComboBox = new JComboBox(books);
		
		setBorder(BorderFactory.createTitledBorder(action + " a Book"));
		
		bookComboBox.addItemListener((ItemListener) new ItemListener() {
	        @Override
	        public void itemStateChanged(ItemEvent e) {
	            if(e.getStateChange() == ItemEvent.SELECTED) {
	            	bookIndex = bookComboBox.getSelectedIndex();
	                author.setText("Author: " + bookList.get(bookIndex).getAuthor());
	                publisher.setText("Author: " + bookList.get(bookIndex).getPublisher());
	            }
	        }
	    });
	    
		welcomeMsg =  new JLabel("Welcome " + firstName);
		
		if (bookList.get(bookIndex).getAuthor().equalsIgnoreCase(""))
			author = new JLabel("");
		else
			author = new JLabel("Author: " + bookList.get(bookIndex).getAuthor());
		
		if (bookList.get(bookIndex).getPublisher().equalsIgnoreCase(""))
			publisher = new JLabel("");
		else
			publisher = new JLabel("Publisher: " + bookList.get(bookIndex).getPublisher());
		
		borrowReturnBtn = new JButton(action);
		borrowReturnBtn.addActionListener(new borrow_return_action_btn());
		goBackBtn = new JButton("Go Back");
		goBackBtn.addActionListener(new go_back_btn());
		
		add(welcomeMsg);
		add(bookComboBox);
		add(author);
		add(publisher);
		add(borrowReturnBtn);
		add(goBackBtn);
		
	}
	
	private class borrow_return_action_btn implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
//			JOptionPane.showMessageDialog(null, "Button Clicked");
			if (action.equalsIgnoreCase("borrow")) 
				borrowABook();
			if (action.equalsIgnoreCase("return")) 
				returnABook();
			
		}
	}
	
	private class go_back_btn implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			new MiddlewayGUI(username, firstName);
			booksPageGUI.dispose();
		}
	}
	
	public void borrowABook() {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
			
			Statement stmt = conn.createStatement();
//			Statement stmt2 = conn.createStatement();
			
			String query = "UPDATE books SET status = 0 WHERE isbn = " + bookList.get(bookIndex).getIsbn();
			
			String query2 = "INSERT INTO borrowing (username, book_isbn) "
					+ "VALUES ('"+ username + "', " + bookList.get(bookIndex).getIsbn() + ")";
			
			int upRows = stmt.executeUpdate(query);
			
			int inRows = stmt.executeUpdate(query2);
			
			if (upRows > 0 && inRows > 0) {
				JOptionPane.showMessageDialog(null, "Your book was successfully borrowed");
//				bookList = getAvailableBooks();
				new BooksPageGUI("Borrow", username, firstName);
				booksPageGUI.dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "Error: Your book was NOT borrowed");
			
			conn.close();			
			
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Coudn't connect to DB");
		}
	}
	
	public void returnABook() {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
			
			Statement stmt = conn.createStatement();
//			Statement stmt2 = conn.createStatement();
			
			String query = "UPDATE books SET status = 1 WHERE isbn = " + bookList.get(bookIndex).getIsbn();
			
			String query2 = "DELETE FROM borrowing WHERE username = '" + username + "' AND book_isbn = " + bookList.get(bookIndex).getIsbn();
			
			int upRows = stmt.executeUpdate(query);
			
			int inRows = stmt.executeUpdate(query2);
			
			if (upRows > 0 && inRows > 0) {
				JOptionPane.showMessageDialog(null, "Your book was successfully returned");
				new BooksPageGUI("Return", username, firstName);
				booksPageGUI.dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "Error: Your book was NOT returned");
			
			conn.close();			
			
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Coudn't connect to DB");
		}
	}
	
	public ArrayList<Book> getBorrowedBooks(){
		
		ArrayList<Book> bookList = new ArrayList<Book>();
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
			
			Statement stmt = conn.createStatement();
			
			String query = "SELECT b.isbn, b.title, b.author, b.publisher "
					+ "FROM books AS b INNER JOIN borrowing AS r "
					+ "ON r.book_isbn = b.isbn WHERE r.username = '" + username + "'";
			
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs.next()){
				do
				{
					bookList.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
				} while(rs.next());
				
			} else {
				bookList.add(new Book(0, "", "", ""));
			}
			
			conn.close();			
			
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Coudn't connect to DB");
		}
		
		return bookList;
		
	}
	
	
	public ArrayList<Book> getAvailableBooks(){
		ArrayList<Book> bookList = new ArrayList<Book>();
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
			
			Statement stmt = conn.createStatement();
			
			String query = "SELECT isbn, title, author, publisher "
					+ "FROM books "
					+ "WHERE status = 1";
			
			ResultSet rs = stmt.executeQuery(query);
			
			if (rs.next()){
				do
				{
					bookList.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
				} while(rs.next());
				
			} else {
				bookList.add(new Book(0, "", "", ""));
			}
			
			conn.close();			
			
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "Coudn't connect to DB");
		}
		
		return bookList;
		
	
	}
	
}
