package GameTest;

import java.awt.Graphics;

import KLD.Game;
import KLD.obj.Player;

public class ExampleGame extends Game {
	Player banana; 
	
	@Override
	public void init() {
		//load animation for player
		l.setExtention(".png");
		l.setFolder("res/ball/ball_");
		//create a player object
		banana = new Player(150, 150, l.loadAnimation("1","2","3","4","5","6","7","8"));
		banana.orbitMouse(50, .12);
		
	}

	@Override
	public void draw(Graphics g) {
		banana.drawMe(g);
	}

	@Override
	public void update() {
		
		banana.callAllAction();
	}

}
