package GameTest;

import java.awt.Graphics;

import javax.swing.JOptionPane;


import KLD.Game;
import KLD.GameFrame;

public class RightGame extends Game {

	@Override
	public void init() {
		name = "RightGame";
		GameFrame.setTitle("You choose the opposite of left");
	}

	@Override
	public void draw(Graphics g) {
		
		g.drawString("Go die", 230, 250);

	}

	@Override
	public void update() {

		if(input.anyKeyIsClicked() || input.mouseIsClicked()){
		JOptionPane.showMessageDialog(null, "You suck!", "Bye-bye", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
		}

	}

}
