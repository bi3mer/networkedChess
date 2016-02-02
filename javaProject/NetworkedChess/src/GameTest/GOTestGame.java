package GameTest;

import java.awt.Color;
import java.awt.Graphics;

import KLD.Game;
import KLD.Input;
import KLD.cmd.Action;
import KLD.obj.GameObject;
import KLD.obj.Player;

public class GOTestGame extends Game {
	Action act; 
	int timer; 
	
	Player p = new Player(50, 50, 20,20);
	GameObject rect = new GameObject(50, 50);
	@Override
	protected void init() {
		name = "Facial";
		rect.setXY(250, 250);
		
		timer = 0; 
		act = new Action("addTimer") {
			
			@Override
			protected void action() {
				timer++; 
				
			}
		};
		addAction(act);
	}

	@Override
	protected void draw(Graphics g) {
		p.drawMe(g);
		rect.drawMe(g);
		
		g.drawString("p isRightTo rect:" + p.isRightTo(rect), 10, 10);
		g.drawString("p isLeftTo rect:" + p.isLeftTo(rect), 10, 30);
		g.drawString("p isOnTopOf rect:" + p.isOnTopOf(rect), 10, 50);
		g.drawString("p isUnder rect:" + p.isUnder(rect), 10, 70);
		g.setColor(Color.red);
		g.drawString("timer: " +timer, 100, 100);
		
	}

	@Override
	protected void update() {
		
		
		if(input.keyIsClicked(Input.ESCAPE)){
			act.deteminate();
		}
		if(input.keyIsClicked(Input.SPACE))
			act.setActivate(!act.isActivated());
		
		p.applyArrowKeys(1);
	}

	 

}
