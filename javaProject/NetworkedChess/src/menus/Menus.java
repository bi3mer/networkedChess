package menus;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import model.ChessPlayerController;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Menus {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Menus window = new Menus();
					window.frame.setVisible(true);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}			//default initiator for each frame

	/**
	 * Create the application.
	 */
	public Menus() 
	{
		initialize();
	}					//this starts the whole app

	/**
	 * Initialize the contents of the frame.
	 */
	private void JsonComp(JSONObject obj, String message)
	{
		try 
		{
			// on successful login/account create go to the MainMenu frame
			if(obj.getInt("status") < 300)	
			{
				frame.dispose();
				MainMenu menu = new MainMenu();
				menu.setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null, message);
			}
		} 
		catch (HeadlessException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	// server return checking for login/createaccount
	
	private void initialize() 
	{
		//the JFrame App instantiates the controller
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setLayout(null);
		
		// Login button instantiation
		JButton btnLogin = new JButton("Login");	
		
		// on button listener
		btnLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					String username = textField.getText();
					String password = passwordField.getText();
					JSONObject returnObj = ChessPlayerController.getInstance().login(username, password);
					JsonComp(returnObj,"Incorrect Password or Non-Existent Account"); // Object check/error ret
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Login Error"); // function error
				}
			}
		});
		
		textField = new JTextField();
		textField.setToolTipText("Username:");
		textField.setBounds(240, 241, 116, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);					//Username field
		
		// Create account button
		JButton btnCreateAccount = new JButton("Create Account");
		
		// add button listener
		btnCreateAccount.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					String username = textField.getText();
					String password = passwordField.getText();
					JSONObject returnObj = ChessPlayerController.getInstance().createAccount(username, password);
					JsonComp(returnObj,"This Username is taken");
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Account Creation Error");
				}
			}
		}); // Account creation works in a similar fashion as login
		
		btnCreateAccount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreateAccount.setBounds(387, 348, 134, 39);
		frame.getContentPane().add(btnCreateAccount); //create account button
		
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnLogin.setBounds(26, 347, 134, 39);
		frame.getContentPane().add(btnLogin);	//login button
		
		passwordField = new JPasswordField();
		passwordField.setBounds(240, 290, 116, 22);
		frame.getContentPane().add(passwordField);	//password field is special so u see ** instead of letters
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Menus.class.getResource("/graphics/CGO.stat.login.png")));
		label_1.setBounds(0, 0, 547, 400); //background
		
		frame.getContentPane().add(label_1);
		frame.setBounds(0, 0, 565, 447);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//frame metrics
	}
}
