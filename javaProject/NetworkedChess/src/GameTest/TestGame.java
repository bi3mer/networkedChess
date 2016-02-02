package GameTest;
 
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import KLD.Console;
import KLD.Game;
import KLD.GameFrame;
import KLD.Console;
import KLD.Console;
import KLD.Input;
import KLD.cmd.Command;
import KLD.cmd.Draw;
import KLD.obj.GameObject;
import KLD.obj.Player;


public class TestGame extends Game {

	//Player p = new Player(0, 0, loader.loadAnimation(""));
	int countDown = 100; 
	
	GameObject KLD = new GameObject(20, 20);
	
	int FPS = Console.getFPS(); 
	
	@Override
	public void init() {
		
		//adding commands (see KLD class)
		KLD.x(  100); 
		KLD.y( 400);
		addDraw(K.drawTest);
		addDraw(K.drawTest2);
		 KLD.moveUp(0, 1);
		
		 //Console.showFPS();
		
	}

	@Override
	public void draw(Graphics g) {
		KLD.callAllDraw(g);
		KLD.drawMe(g);

		g.drawString("Count down: " + countDown, 200,200);

	}

	@Override
	public void update() {
		KLD.callAllAction();
		
		
		if(input.keyIsClicked(Input.DOWN)){
			FPS -= 5; 
			Console.setFPS(FPS);
		}
		if(input.keyIsDown(Input.UP)){
			FPS += 5; 
			Console.setFPS(FPS);
		}
		
		if(input.mouseIsClicked()){
			
			KLD.addCommand(new Command("MoveTo+Dot", KLD) {
				private Point p  ;
				
				int cd = 15;
				
				@Override
				public void init(){
					p = new Point(Input.point.x, Input.point.y);
					//adds a new command
					KLD.moveTo(p, 5 );
				}
				@Override
				public void action() {
					if(cd-- == 0){
						
						deteminate();
					}
				}
				
				
				@Override
				public void draw(Graphics g) {
					g.setColor(Color.BLACK);
					g.drawOval(p.x, p.y, 3, 3);
					
				}
				
			});
		}
		
		if(countDown==0){
			GameFrame.setTitle("YOLO");
			Console.setGame(AwesomeGame.class);
		}
		
		
			
		countDown--; 
		
		

	}

	

}
