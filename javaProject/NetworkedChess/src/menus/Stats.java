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
import javax.swing.JFormattedTextField;

public class Stats extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChessPlayerController player = new ChessPlayerController();
					Stats frame = new Stats(player);
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
	public Stats(ChessPlayerController player) {		//Stats frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 547, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnReturn = new JButton("Return");	//return button
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					contentPane.setVisible(false);
					dispose();
					MainMenu menu = new MainMenu(player);
					menu.setVisible(true);
					}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, "Return to Main error");
				}
			}
		});
		btnReturn.setBounds(401, 304, 97, 25);
		contentPane.add(btnReturn);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(261, 169, 56, 16);
		contentPane.add(lblNewLabel_2);	//win text field for input
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(261, 219, 56, 16);
		contentPane.add(lblNewLabel);	//draw text field for input
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(261, 270, 56, 16);
		contentPane.add(lblNewLabel_1);	//loss text field for input
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Stats.class.getResource("/graphics/CGO.stat.screen.png")));
		label.setBounds(0, 0, 529, 353);
		contentPane.add(label);			//background info
		
		int w = 0;
		int l = 0;
		int d = 0;	//init stat vars
		try {
			JSONObject rats = player.ratings();
			w = rats.getInt("win");
			l = rats.getInt("loss");
			d = rats.getInt("draw");	//retrieve our stats
		} catch (JSONException | IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		lblNewLabel.setText(Integer.toString(d));
		lblNewLabel_1.setText(Integer.toString(l));
		lblNewLabel_2.setText(Integer.toString(w));	//set the stat fields 
	}
}
