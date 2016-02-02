package GameTest;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


import KLD.Game;
import KLD.GameFrame;
import KLD.Console;
import KLD.Input;
import KLD.cmd.Command;
import KLD.obj.Player;

public class AwesomeGame extends Game {

	//this game will draw a circle that orbits around the center. 
	
	private double pi; // the angle
	private final int r = 200;  //radios of orbiting 
	
	private int w; 
	private int h; 
	
	boolean wInc;  //controls inc and dec of w 
	boolean hInc; //controls inc and dec of h 
	
	
	private Command followMouse ; //will make the circle orbit around the mouse; 
	private Command timeBar;      //this will show how much time left before followMouse is activated by drawing a bar and decreasing counter
	private int counter = 120; 		//when this counter reach 0, the game will switch to AwesomeGame
	
	
	private Player player;
	
	@Override
	public void init() {
		Console.setFPS(20);
		name = "awesome game";
		pi=0;
		w=20;
		h=w;
		//set pi,  w, h to 0; 
		
		//set wInc and hINc randomly
		wInc = new Random().nextBoolean();
		hInc = new Random().nextBoolean(); 
			
		//this command will draw two rects [left and right], and also draws players. Additionally, draws a ball the will orbits around the mouse. 
		followMouse = new Command("FM") {
			
			@Override
			public void action() {
			
				if(player.x() < 100){
					Console.setGame(LeftGame.class);
				}
				else if (player.x() > Console.maxWidth-100 - player.width()){
					Console.setGame(RightGame.class);
				}
				
				player.applyMovingLeft(Input.LEFT, 4);
				player.applyMovingRight(Input.RIGHT, 4);
				
				//reverse the animation when space is clicked
				if(Console.input.keyIsClicked(Input.SPACE))
					player.animation.setReversed(!player.animation.isReversed());
				
			}

			@Override
			public void draw(Graphics g) {
				g.fillOval((int) (20*Math.cos(pi)) + Input.point.x, (int)(20*Math.sin(pi)) + Input.point.y, 10+w%3, 10+h%3);		
				
				//player will draw himself
				player.drawMe(g);
				
			
				 //draw two rectangles
				g.setColor(Color.CYAN.brighter());
				g.fillRect(0, 0, 100, Console.maxHeight);
				g.setColor(Color.CYAN.darker());
				g.fillRect(Console.maxWidth-100, 0, 100, Console.maxHeight);
			}	
		};
		followMouse.disable();
		addCommand(followMouse);
		
		
		timeBar = new Command("timeBar") {


			@Override
			public void action() {
				if(counter>0){
					counter--; 
					if(counter==0){
						GameFrame.setTitle("Left or Right?");
						followMouse.enable(); //enable FM
						deteminate(); //removes this command from the list
					}
			}
				
			}

			@Override
			public void draw(Graphics g) {
				Color c = g.getColor(); //save previous color
				
				
				
				g.setColor(Color.YELLOW);
				//draw bar at the bottom 
				g.fillRect(0, Console.maxHeight- 20, 1+counter*2, 10);
				//set Color to what it was
				g.setColor(c);
			}//end if 
			
		
		};//end timeBar 
		
		addCommand(timeBar);
		Console.loader.setExtention(".png");
		Console.loader.setFolder("res/ball/ball_");
		player = new Player(Console.maxWidth/2, Console.maxHeight-50, Console.loader.loadAnimation("1","2","3","4","5","6","7","8"));
		
	}

	@Override
	public void draw(Graphics g) {
		//fill Oval with x position of cos(pi)*r   and so for y pos. w and h for width and height
		g.setColor(Color.BLACK);
		
		if(followMouse.isDisabled())
		g.fillOval((int) (r*Math.cos(pi)) + ((Console.maxWidth-w)/2), (int)(r*Math.sin(pi)) +((Console.maxHeight-h)/2), w, h);
		
	
	}

	@Override
	public void update() {

		
		//increase the angle
		pi+= 0.1; 
		
		// let the width and height bounce between 10 - 50;
		
		if(wInc)
			w+=5; 
		else 
			w-=5; 
		
		
		if(hInc)
			h+=3;
		else 
			h-=3; 
		
		
		if(w<= 10 || w>=50)
			wInc= !wInc; 
		
		if(h<= 10 || h>=50)
			hInc= !hInc; 
		
		
	}

	

}
