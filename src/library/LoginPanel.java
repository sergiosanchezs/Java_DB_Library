package library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginPanel extends JPanel {
	JTextField usernameField;
	JPasswordField passwordField;
	JButton loginBtn, registerBtn;
	LoginPageGUI loginGUI;
	String user = "", pass = "", firstName = "";
	JLabel userTxt, passTxt;
	
	public LoginPanel(LoginPageGUI lgGUI) {
		// Create a GridLayout manager with
		// 3 rows and 1 column.
		loginGUI = lgGUI;
		setLayout(new GridLayout(8, 1));
		
		setBorder(BorderFactory.createTitledBorder("Login"));
		
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		loginBtn = new JButton("Login");
		loginBtn.addActionListener(new login_btn());
		
		registerBtn = new JButton("Register a New User");
		registerBtn.addActionListener(new register_btn());
		
		userTxt =  new JLabel("Username");
		passTxt =  new JLabel("Password");
		
		add(userTxt);
		add(usernameField);
		add(passTxt);
		add(passwordField);
		add(loginBtn);
		add(registerBtn);
		
	}
	
	private class register_btn implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			new RegisterGUI();
			loginGUI.dispose();
		}
	}
	
	private class login_btn implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			if (usernameField.getText().equals("") || String.valueOf(passwordField.getPassword()).equals("")) {
				JOptionPane.showMessageDialog(null, "Error: You must fill out all the input fields.");
				
			} else {
				
				try {
					
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
					
					Statement stmt = conn.createStatement();
					
					ResultSet rs = stmt.executeQuery("SELECT username, password, first_name FROM users WHERE username = '" + usernameField.getText() + "'");
					
					if(rs.next()) {
						user = rs.getString(1);
						pass = rs.getString(2);
						firstName = rs.getString(3);
					}
					
					conn.close();
					
					if (pass.equals(String.valueOf(passwordField.getPassword()))) {
						new MiddlewayGUI(user, firstName);
						loginGUI.dispose();
					} else
						JOptionPane.showMessageDialog(null, "Username/Password incorrect.");
					
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Coudn't connect to DB");
				}
			}
			
			
			
			
			
		}
	}
}
