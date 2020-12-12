package library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RegisterPanel extends JPanel {
	
	RegisterGUI registerGUI;
	JLabel usernameLbl, passwordLbl, firstNameLbl, lastNameLbl, addressLbl;
	JTextField username, firstName, lastName, address;
	JPasswordField passwordField;
	JButton registerBtn, goBackBtn;
	
	public RegisterPanel(RegisterGUI regGUI) {
		registerGUI = regGUI;
		setLayout(new GridLayout(13, 1));
		
		setBorder(BorderFactory.createTitledBorder("Registration"));

		usernameLbl = new JLabel("Username");
		passwordLbl = new JLabel("Password");
		firstNameLbl = new JLabel("First Name");
		lastNameLbl = new JLabel("Last Name");
		addressLbl = new JLabel("Address");
		
		username = new JTextField();
		firstName = new JTextField();
		lastName = new JTextField();
		address = new JTextField();
		passwordField = new JPasswordField();
		
		registerBtn = new JButton("Register");
		registerBtn.addActionListener(new register_btn());
		
		goBackBtn = new JButton("Go Back");
		goBackBtn.addActionListener(new go_back_btn());
		
		add(usernameLbl);
		add(username);
		add(passwordLbl);
		add(passwordField);
		add(firstNameLbl);
		add(firstName);
		add(lastNameLbl);
		add(lastName);
		add(addressLbl);
		add(address);
		add(registerBtn);
		add(goBackBtn);
		
	}
	
	private class go_back_btn implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			new LoginPageGUI();
			registerGUI.dispose();
		}
	}
	
	private class register_btn implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			
			if (username.getText().equals("") || String.valueOf(passwordField.getPassword()).equals("") || firstName.getText().equals("") || lastName.getText().equals("") || address.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Error: You must fill out all the input fields.");
				
			} else {
				
				try {
					
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
					
					Statement stmt = conn.createStatement();
					
					String query = "INSERT INTO users (username, password, first_name, last_name, address) "
							+ "VALUES ('" + username.getText() + "', "
							+ "'" + String.valueOf(passwordField.getPassword()) +  "', "
							+ "'" + firstName.getText() +  "', "
							+ "'" + lastName.getText() +  "', "
							+ "'" + address.getText() +  "'"
							+ ")";
					
					int upRows = stmt.executeUpdate(query);
					
					if (upRows > 0) {
						JOptionPane.showMessageDialog(null, "Your account was successfully created");
						new LoginPageGUI();
						registerGUI.dispose();
					}
					else
						JOptionPane.showMessageDialog(null, "Error: We could NOT create your acount.");
					
					conn.close();			
					
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Coudn't connect to DB");
				}
				
			}
			
		}
	}

}
