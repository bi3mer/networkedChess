package GameTest;

import java.awt.Graphics;


import KLD.Game;
import KLD.cmd.Draw;
import KLD.cmd.TimedDraw;
import KLD.obj.Player;

public class KnewGame extends Game {

	
	Player player; 
	
	@Override
	public void init() {
		/*
		 * Rect <- width & h 
		 * Image
		 * Animation 
		 * 
		 */
		l.setFolder("res/ball/ball_");
		l.setExtention(".png");
		player  = new Player(20, 20, l.loadAnimation("1,2,3,4,5,6,7,8".split(","))); 
		addDraw(new TimedDraw("Tips", 20) {
			
			@Override
			public void draw(Graphics g) {
				g.drawString("Use WASD to move", 50, 50);
				
			}
		});
	}

	@Override
	public void draw(Graphics g) {
		player.drawMe(g);
	}

	@Override
	public void update() {
		
		player.applyWASD(5);
		
	}

	
	
	
}
