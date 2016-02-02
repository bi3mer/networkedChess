package GameTest;

import java.awt.Graphics;

import KLD.Game;
import KLD.GameFrame;
import KLD.Console;
import KLD.Input;
import KLD.cmd.TimedDraw;

public class LeftGame extends Game {

	@Override
	public void init() {
		name = "LeftGame";
		GameFrame.setTitle("You choose the opposite of right");
		
		TimedDraw guild = new TimedDraw("Guide", 20) {
			
			@Override
			public void draw(Graphics g) {
				g.drawString("User Arrow Keys", maxWidth/2, maxHeight/2);
				
			}
		};
		addDraw(guild); //I miss spelled guide, but who cares
	}

	@Override
	public void draw(Graphics g) {
		K.whatToDo.callDraw(g);
	}

	@Override
	public void update() {

		K.whatToDo.callAction();
		
	}

}
