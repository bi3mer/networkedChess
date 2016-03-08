package menus;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;
import org.json.JSONObject;

import GamePanels.Launcher;
import GamePanels.SinglePlayerChess;
import KLD.GameFrame;
import KLD.util.BackgroundQueue;
import model.ChessPlayerController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Queue extends JFrame {

	private JPanel contentPane;
	private BackgroundQueue bgQueue;

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
					Queue frame = new Queue();
					frame.setVisible(true);
				} 
				catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public Queue()
	{	
		//Queue Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 318, 155);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnCancel = new JButton("Cancel");		//Queue Cancel button
		btnCancel.setBounds(100, 70, 97, 25);
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					// Destroy thread
					bgQueue.terminateThread();
					
					// Load main menu
					contentPane.setVisible(false);
					dispose();
					MainMenu main = new MainMenu();
					main.setVisible(true);				//just brings us back to main menu if pressed
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Queue Cancel Error"); // function error
				}
			}
		});
		contentPane.setLayout(null);
		
		JLabel time = new JLabel("");
		time.setFont(new Font("Tahoma", Font.PLAIN, 18));
		time.setBounds(164, 44, 56, 16);
		contentPane.add(time);
		contentPane.add(btnCancel); //timer
		
		class Counter{
		int timer = 0;
		}
		Counter counted = new Counter();
		ActionListener listun = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				counted.timer+=1;
				time.setText(Integer.toString(counted.timer));
			}
			
		};
		Timer rimer = new Timer(1000,listun);
		rimer.start();
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 299, 108);
		label.setIcon(new ImageIcon(Queue.class.getResource("/graphics/Queue.png")));
		contentPane.add(label);		//background
		
		try 
		{
			// Add self to matchmaking
			ChessPlayerController.getInstance().getMatchMaking();
			
			// Start bg queue
			this.bgQueue = new BackgroundQueue(this);
			this.bgQueue.start();
		}
		catch (JSONException | IOException | InterruptedException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * Enter the game
	 * @param otherUser
	 * @param isWhite
	 */
	public void enterGame(String otherUser, Boolean isWhite)
	{
		// Kill Queue frame
		this.dispose();
		
		// Add vars to controller
		ChessPlayerController.getInstance().isWhite = isWhite;
		ChessPlayerController.getInstance().otherPlayer = otherUser;
		
		// Launch Game
		try 
		{
			GameFrame.launch();
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
