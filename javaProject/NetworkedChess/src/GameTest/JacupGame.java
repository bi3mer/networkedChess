package GameTest;

import java.awt.Graphics;


import KLD.Animation;
import KLD.Console;
import KLD.Game;
import KLD.Input;
import KLD.Loader;
import KLD.obj.GameObject;
import KLD.obj.Player;

public class JacupGame extends Game {

	Player p ; 
	
	@Override
	protected void init() {
		Console.setFPS(20);
		p = new Player(50,50,50,50);
	}

	@Override
	protected void draw(Graphics g) {
		 p.drawMe(g);
	}

	@Override
	protected void update() {
		p.applyArrowKeys(20);
		
		if(input.keyIsClicked(Input.SPACE))
			p.setXY(200, 200);

	}

}
