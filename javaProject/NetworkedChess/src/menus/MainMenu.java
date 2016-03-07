package menus;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.ChessPlayerController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MainMenu extends JFrame {

	private JPanel contentPane;

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
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}	//default initiator for each frame

	/**
	 * Create the frame.
	 */
	public MainMenu() {	//main menu frame, pass player as arg
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 565, 447);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);	//frame definitions
		
		// play button
		JButton btnPlay = new JButton("Play");
		btnPlay.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPlay.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					contentPane.setVisible(false);
					dispose();
					
					// Create queue window
//					Queue que = new Queue(player);
					Queue que = new Queue();
					que.setVisible(true);
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Queueing Error"); // function error
				}
			}
		});
		btnPlay.setBounds(199, 164, 154, 49);
		contentPane.add(btnPlay);
		
		JButton btnStats = new JButton("Stats");	//Stats button
		btnStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					contentPane.setVisible(false);
					dispose();
//					Stats stat = new Stats(player);
					Stats stat = new Stats();
					stat.setVisible(true);		//this takes us to the stats frame
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Stat Access Error"); // function error
				}
			}
		});
		btnStats.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnStats.setBounds(199, 261, 154, 49);
		contentPane.add(btnStats);
		
		JLabel label = new JLabel("");	//just backgound stuff
		label.setBounds(0, 0, 547, 400);
		label.setIcon(new ImageIcon(MainMenu.class.getResource("/graphics/CGO.main.screen.png")));
		contentPane.add(label);
	}

}
