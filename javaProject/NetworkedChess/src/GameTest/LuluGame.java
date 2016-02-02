package GameTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import KLD.Game;
import KLD.Input;
import KLD.obj.GameObject;
import KLD.obj.Player;

public class LuluGame extends Game {

	
	Player shape1 = new Player(50,50, 50, 50);
	
	
	int delta = 1; 
	
	@Override
	protected void init() {
	//shape1.shape = new Ellipse2D.Double(shape1.x(), shape1.y(), shape1.width(), shape1.height());
		 

	}
	@Override
	protected void draw(Graphics g) {
		
		g.setColor(Color.BLUE);
		setFade(g, .5f);
		shape1.drawMe(g);
		 
		
		g.drawString("Speed: "+ delta, 10, 10);
	}

	
	//
	
	 
	
	@Override
	protected void update() {
		
		
		shape1.applyArrowKeys(delta);
		
		
		if(input.keyIsClicked(Input.SHIFT))
			delta++; 
		else if(input.keyIsClicked(Input.SPACE) && delta!=1)
			delta--;
	
		
		
	}

}
