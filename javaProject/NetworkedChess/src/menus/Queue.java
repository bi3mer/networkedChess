package menus;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;
import org.json.JSONObject;

import model.ChessPlayerController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Queue extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChessPlayerController player = new ChessPlayerController();
					Queue frame = new Queue(player);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//default initiator for each frame
	/**
	 * Create the frame.
	 */
	public Queue(ChessPlayerController player) {		//Queue Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 318, 155);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCancel = new JButton("Cancel");		//Queue Cancel button
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					contentPane.setVisible(false);
					dispose();
					MainMenu main = new MainMenu(player);
					main.setVisible(true);				//just brings us back to main menu if pressed
					
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Queue Cancel Error"); // function error
				}
			}
		});
		
		
		btnCancel.setBounds(100, 70, 97, 25);
		contentPane.add(btnCancel);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Queue.class.getResource("/graphics/Queue.png")));
		label.setBounds(0, 0, 299, 108);
		contentPane.add(label);		//background
		
		JSONObject mmv;
		try {
			mmv = player.getMatchMaking();
			if(mmv.getInt("status")> 200)
			{
				while(player.getUpdate(false).getInt("status") > 200);	//This is where matchmaking is checked
			}
			else
			{
				//if the initial getMatchmaking returns the data without que we can just start game
				//Khaled, mmv contains the matchmaking info you need
				//player info is stored in player
			}
		} catch (JSONException | IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
